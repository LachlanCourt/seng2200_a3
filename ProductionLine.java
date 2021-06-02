/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class is the main base of calculations in the program run. It has
 ****    methods to initialise, produce and report, and also includes functionality
 ****    to read the production line from a text file
 *******************************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class ProductionLine
{
    // Record the top and tail of the ProductionLine
    private InitialStage startStage;
    private FinalStage endStage;

    // ArrayLists of the working elements of the ProductionLine
    private ArrayList<Stage> stages;
    private ArrayList<StorageQueue<Item>> queues;

    /*
     Used to control the run of the simulation. completionTimes is a PriorityQueue so we will always get the next
     TimeEvent due to finish
     */
    private PriorityQueue<TimeEvent> completionTimes;
    private double currentTime;
    private static final double MAX_TIME = 10000000;

    // Random instance for generating processing times
    private Random rd;

    // Main Constructor
    public ProductionLine(double M_, double N_, int Qmax_, String filename)
    {
        rd = new Random(24601); // One Day More

        // Try loading from a file. If it fails (If filename is null then it will fail), load the default line.
        if (!initialiseLineFromFile(M_, N_, Qmax_, filename))
        {
            initialiseDefaultLine(M_, N_, Qmax_);
        }

        completionTimes = new PriorityQueue<>();
        currentTime = 0;
    }

    /**
     * Used to run a single simulation of the production line
     */
    public void produce()
    {
        TimeEvent temp;
        // Main run
        while (currentTime < MAX_TIME)
        {
            // Loop until all stages are effectively "Busy" - No modification is happening in the queues whether adding or removing
            do
            {
                /*
                 Start by assuming there is no modification in the queues by setting the flag to true. If a Stage
                 modifies a Queue it will set the flag to false
                 */
                startStage.resetModificationFlag();
                // Loop through every stage
                for (int i = 0; i < stages.size(); i++)
                {
                    // Ask the stage to process and pass it the current simulation time
                    temp = stages.get(i).process(currentTime);
                    // If the process method has returned a TimeEvent (not null), add it to the completionTimes Queue
                    if (temp != null)
                    {
                        completionTimes.add(temp);
                    }
                }
            }
            while (startStage.getModificationFlag());
            /*
             If any of the Queues were modified, loop again. Some of the Queues may have just had Items added or removed
             which might allow other Blocked or Starved Stages to operate. Make sure they're all done before continuing
             */

            // Now that all of the stages are actively processing, jump to the next time event
            currentTime = completionTimes.remove().getCompletionTime();
        }
    }

    /**
     * Used to run a report on the last simulation of the production line
     * @return A formatted String containing the report data
     */
    public String report()
    {
        // Start by declaring the heading
        String working = "PRODUCTION REPORT\n\n";

        // Get the ArrayList of all Items that reached the end of the production line
        ArrayList<Item> itemReport = endStage.report();

        // Report some general data
        working += "Number of items produced: " + itemReport.size() + "\n";
        working += "Time taken: " + (int) MAX_TIME + " units\n\n";

        // PRODUCTION STAGES -------------------------------------------------------------------------------------------
        working += "Production Stages:\n\n";
        // Loop through each stage to produce a line in the report for each
        for (int i = 0; i < stages.size(); i++)
        {
            // Calculate the time stopped to calculate the work percent of the Stage
            double timeStopped = stages.get(i).getTimeStarved() + stages.get(i).getTimeBlocked();
            // Calculate the work percent
            String work = String.format("%2.4f", (100 - (timeStopped / MAX_TIME) * 100));
            // Calculate some additional spaces for formatting
            String s1 = " ".repeat(3 - stages.get(i).getId().length());
            String s2 = " ".repeat(8 - work.length());
            String s3 = " ".repeat(15 - String.format("%5.4f", stages.get(i).getTimeStarved()).length());

            // Report the name of the stage, the work percent, the time starved, and the time blocked
            working += stages.get(i).getId() + ":" + s1 + "    Work: " + s2 + work + "%    Starved: " + String.format("%5.4f", stages.get(i).getTimeStarved()) + s3 + "    Blocked: " + String.format("%5.4f", stages.get(i).getTimeBlocked()) + "\n";
        }

        // STORAGE QUEUES ---------------------------------------------------------------------------------------------
        working += "\nStorage Queues:\n\n";
        // Loop through each queue to produce a line in the report for each
        for (int i = 0; i < queues.size(); i++)
        {
            // Report the name of the queue, the average items in the queue, and the average time an item spends in the queue
            working += queues.get(i).getID() + ":    " + "AvgItems: " + String.format("%5.4f", queues.get(i).getAvgItems(MAX_TIME)) + "    AvgTime: " + String.format("%5.4f", queues.get(i).getAvgTime()) + "\n";
        }

        // PRODUCTION PATHS -------------------------------------------------------------------------------------------
        /*
         Declare a hashmap to store the number of items that followed each path in the production line. Using a hashmap
         and calculating the path logically allows for a line to be read from a text file in any topology
         */
        HashMap<String, Integer> pathData = new HashMap<>();
        // Loop through every item that finished production
        for (int i = 0; i < itemReport.size(); i++)
        {
            // Grab the item out of the arraylist to work with it easier
            Item currentItem = itemReport.get(i);
            // Get the arraylist of ProcessEvent's that the item has stored as it travels through the production line
            ArrayList<ProcessEvent> processReport = currentItem.report();

            // Initialise a String to store the ID's of the Stages that the item passed through
            String pathCode = "";
            // Loop through each process
            for (int j = 0; j < processReport.size(); j++)
            {
                // Add the ID of the Stage associated with that process, followed by an arrow
                pathCode += processReport.get(j).getStageID() + " -> ";
            }
            // Remove the last arrow as it is not necessary
            pathCode = pathCode.substring(0, pathCode.length() - 4);
            // If the pathCode that has been generated already exists, increment the integer stored there
            if (pathData.containsKey(pathCode))
            {
                pathData.put(pathCode, pathData.get(pathCode) + 1);
            }
            else
            {
                /*
                 If the pathCode does not exist, create it in the hashmap and initialise it to 1 for the item in
                 question
                 */
                pathData.put(pathCode, 1);
            }
        }

        working += "\nProduction Paths:\n\n";
        // Loop through the hashmap
        for (String key : pathData.keySet())
        {
            // Report the key (The production path) followed by the data (The count of items that followed that path)
            working += key + ": " + pathData.get(key) + "\n";
        }

        // Output to text file
        PrintWriter out;
        try
        {
            // Declare a new PrintWriter
            out = new PrintWriter("production_report.txt");
        }
        catch (FileNotFoundException e)
        {
            // If there is an error opening the PrintWriter, that's okay, just return the console data
            System.err.println(e);
            return working;
        }
        // Output to file
        out.println(working);
        out.close();

        // If there are no errors outputting to the file, add a final success message to the string before returning
        return working + "\nProgram complete. Data outputted to \"production_report.txt\"";
    }

    /**
     * Initialise the default production line if there are any errors reading the line from a text file, or if one was
     * not specified
     */
    public void initialiseDefaultLine(double M, double N, int Qmax)
    {
        // Inform user
        System.out.println("Loading default production line...\n");

        // Initialise arraylist of queues
        queues = new ArrayList<>();

        // Initialise each Queue and add it to the arraylist
        StorageQueue<Item> Q01 = new StorageQueue<Item>(Qmax, "Q01");
        queues.add(Q01);
        StorageQueue<Item> Q12 = new StorageQueue<Item>(Qmax, "Q12");
        queues.add(Q12);
        StorageQueue<Item> Q23 = new StorageQueue<Item>(Qmax, "Q23");
        queues.add(Q23);
        StorageQueue<Item> Q34 = new StorageQueue<Item>(Qmax, "Q34");
        queues.add(Q34);
        StorageQueue<Item> Q45 = new StorageQueue<Item>(Qmax, "Q45");
        queues.add(Q45);

        // Initialise arraylist of stages
        stages = new ArrayList<>();

        // The initial stage requires a few extra arguments that are used by the entire production line
        InitialStage S0 = new InitialStage(Q01, "S0", M, N, rd);
        stages.add(S0);
        MidStage S1 = new MidStage(Q01, Q12, "S1");
        stages.add(S1);

        // Add the remaining stages, passing a 2 as a multiplicative processingFactor where necessary

        MidStage S2A = new MidStage(Q12, Q23, "S2A", 2);
        stages.add(S2A);
        MidStage S2B = new MidStage(Q12, Q23, "S2B", 2);
        stages.add(S2B);

        MidStage S3 = new MidStage(Q23, Q34, "S3");
        stages.add(S3);

        MidStage S4A = new MidStage(Q34, Q45, "S4A", 2);
        stages.add(S4A);
        MidStage S4B = new MidStage(Q34, Q45, "S4B", 2);
        stages.add(S4B);

        FinalStage S5 = new FinalStage(Q45, "S5");
        stages.add(S5);

        // Label which stages are identified as the start and end of the line
        startStage = S0;
        endStage = S5;
    }

    /**
     * Try to load the production line topology from a text file according to the specified filename
     * @return whether load was successful or not. If unsuccessful then the default line will be loaded
     */
    public boolean initialiseLineFromFile(double M, double N, int Qmax, String filename)
    {
        // Declare Scanner to read from the file
        Scanner input;
        try
        {
            input = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e)
        {
            return false;
        }
        catch (NullPointerException e)
        {
            // If no filename is specified as an argument in program execution, the filename argument will be null
            return false;
        }

        try
        {
            // Read Queues
            queues = new ArrayList<>();
            // While there are still Queues (Starting with "Q") to be read, loop
            while (input.hasNext("Q.*"))
            {
                // Add a new queue, labelling it with the name given in the file
                queues.add(new StorageQueue<Item>(Qmax, input.nextLine()));
            }

            // Read Stages
            stages = new ArrayList<>();

            // Assume the first stage is the InitialStage
            if (input.hasNext("S.*"))
            {
                // Grab the elements from the line
                String id = input.next();
                String nextId = input.next();

                // Get a reference to the Queue that will be the next of the Stage
                StorageQueue<Item> next = getQueueByID(nextId);
                // If the Queue requested doesn't exist, the load has failed
                if (next == null)
                {
                    return false;
                }

                // Assume the processingFactor is 1 unless explicitly specified
                double processingFactor = 1;
                // If a double exists next, assume it's a processingFactor
                if (input.hasNextDouble())
                {
                    processingFactor = input.nextDouble();
                }
                // Add a new InitialStage with the data gathered
                stages.add(new InitialStage(next, id, M, N, rd, processingFactor));
            }

            // While there are still Stages (Starting with "S") to be read, loop
            while (input.hasNext("S.*"))
            {
                // Grab the elements from the line
                String id = input.next();
                String prevId = input.next();
                String nextId = input.next();

                // Get a reference to the Queue that will be the next and prev of the Stage
                StorageQueue<Item> prev = getQueueByID(prevId);
                StorageQueue<Item> next = getQueueByID(nextId);
                // If either Queue requested doesn't exist, the load has failed
                if (next == null || prev == null)
                {
                    return false;
                }

                // Assume the processingFactor is 1 unless explicitly specified
                double processingFactor = 1;
                // If a double exists next, assume it's a processingFactor
                if (input.hasNextDouble())
                {
                    processingFactor = input.nextDouble();
                }
                // Add a new InitialStage with the data gathered
                stages.add(new MidStage(prev, next, id, processingFactor));
            }

            // Final stage indicated by escape character "F"
            if (input.next().compareTo("F") == 0)
            {
                // Grab the elements from the line
                String id = input.next();
                String prevId = input.next();

                // Get a reference to the Queue that will be the prev of the Stage
                StorageQueue<Item> prev = getQueueByID(prevId);
                // If the Queue requested doesn't exist, the load has failed
                if (prev == null)
                {
                    return false;
                }

                // Assume the processingFactor is 1 unless explicitly specified
                double processingFactor = 1;
                // If a double exists next, assume it's a processingFactor
                if (input.hasNextDouble())
                {
                    processingFactor = input.nextDouble();
                }
                // Add a new InitialStage with the data gathered
                stages.add(new FinalStage(prev, id, processingFactor));

                // Label which stages are identified as the start and end of the line
                startStage = (InitialStage) stages.get(0);
                endStage = (FinalStage) stages.get(stages.size() - 1);
            }
            // Notify user that a file has been loaded
            System.out.println("Interpretting production line from text file...\n");
            return true;
        }
        catch (Exception e)
        {
            // If any error occurs during load, notify user and load default line
            System.err.println("Invalid text file format");
            return false;
        }
    }

    /**
     * Used to get the reference to a queue based on it's ID
     * @param nextID the ID of the Queue to search for
     * @return the reference to the requested Queue, or null if it doesn't exist
     */
    private StorageQueue<Item> getQueueByID(String nextID)
    {
        // Loop through the queues that currently exist
        for (int i = 0; i < queues.size(); i++)
        {
            // If there is a match, return the reference
            if (queues.get(i).getID().compareTo(nextID) == 0)
            {
                return queues.get(i);
            }
        }
        // If no matches are found, return null
        return null;
    }
}
