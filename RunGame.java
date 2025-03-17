import java.util.Random;

public class RunGame
{
    public void start()
    {
        int maxGames = 10000;
        Random random = new Random();
        int wins = 0;

        for(int i = 1; i <= maxGames; i++)
        {
            Door door1 = new Door(true, random.nextInt(3) + 1); //assigns a random number to each door, with only 1 door having the prize
            Door door2 = new Door(false, random.nextInt(3) + 1);
            Door door3 = new Door(false, random.nextInt(3) + 1);

            while (door1.getNumber() == door2.getNumber()) //makes sure door1 and door2 don't have the same number
            {
                door2.setNumber(random.nextInt(3) + 1);
            }

            while (door2.getNumber() == door3.getNumber() || door1.getNumber() == door3.getNumber()) //makes sure door 3 doesn't have the same number as doors 1 and 2
            {
                door3.setNumber(random.nextInt(3)+1);
            }

            if (door1.getNumber() == 1) //always chooses door 1, so whenever the "correct" door is assigned the number 1, a win gets added
            {
                wins++;
            }
        }
        
        double prob = (double)wins/maxGames * 100; //calculating probability in percentage form

        System.out.println("Probability while not changing the picked door: " + prob + "%");

        wins = 0;

        for(int i = 1; i <= maxGames; i++)
        {
            Door door1 = new Door(true, random.nextInt(3) + 1); //assigns a random number to each door, with only 1 door having the prize
            Door door2 = new Door(false, random.nextInt(3) + 1);
            Door door3 = new Door(false, random.nextInt(3) + 1);

            while (door1.getNumber() == door2.getNumber()) //makes sure door1 and door2 don't have the same number
            {
                door2.setNumber(random.nextInt(3) + 1);
            }

            while (door2.getNumber() == door3.getNumber() || door1.getNumber() == door3.getNumber()) //makes sure door 3 doesn't have the same number as doors 1 and 2
            {
                door3.setNumber(random.nextInt(3)+1);
            }

            int rand = random.nextInt(3) + 1; //chooses a random door

            if (door1.getNumber() == rand) //if the random door it chose the first time has to prize, it adds a win
            {
                wins++;
            }
            else //otherwise, it gets rid of that door and the contestant gets to choose again
            {
                door1 = new Door(true, random.nextInt(2) + 1); //assigns a random number to each door, with only 1 door having the prize
                door2 = new Door(false, random.nextInt(2) + 1);

                while (door1.getNumber() == door2.getNumber()) //makes sure door1 and door2 don't have the same number
                {
                    door2.setNumber(random.nextInt(3) + 1);
                }

                rand = random.nextInt(2) + 1; //chooses a random door

                if (door1.getNumber() == rand) //if the random door it chose the second time has to prize, it adds a win
                {
                    wins++;
                }
            }

        }

        prob = (double)wins/maxGames * 100; //calculating probability in percentage form

        System.out.println("Probability while changing the picked door: " + prob + "%");
    }
}
