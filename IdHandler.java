import java.util.Random;

public class IdHandler
{
        private static IdHandler instance = null;

        private static Random IDrd;

        private IdHandler()
        {
            IDrd = new Random(100);
        }

        public static String getID()
        {
            if (instance == null)
                instance = new IdHandler();

            String id = "";
            for (int i = 0; i<20;i++)
            {
                id += Character.toString(IDrd.nextInt(25) + 97);
            }
            return id;
        }
}
