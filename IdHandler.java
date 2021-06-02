/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class is a singleton designed to give each Item a unique ID
 *******************************************************************************/

import java.util.Random;

public class IdHandler
{
    // Assume the instance does not exist. Declare it static so that it will be the same for all instantiations
    private static IdHandler instance = null;

    // Define the random number generation
    private static Random IDrd;

    // A private constructor means that the class can only construct an instance of itself. Set a random seed
    private IdHandler()
    {
        IDrd = new Random(100);
    }

    public static String getID()
    {
        // If the instance does not exist, instantiate it which will generate the random seed
        if (instance == null)
        {
            instance = new IdHandler();
        }

        // Generate an ID. Start with an empty String
        String id = "";
        // Generate an ID 20 characters long and then return
        for (int i = 0; i < 20; i++)
        {
            // Pick a random lowercase character to add to id
            id += Character.toString(IDrd.nextInt(25) + 97);
        }
        return id;
    }
}
