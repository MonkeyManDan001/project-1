import java.util.Random;

public class PokemonGame
{
    public static void main(String[] args)
    {
        Card[] deck = new Card[60]; //declares a deck of 60 cards
        Card[] currHand = new Card[7]; //declares the current hand of player consisting of 7 cards
        Card[] prizes = new Card[6]; //declares the prize pile of 6 cards
        int sims = 10000;
        int candyAmt = 4; //candy amount the simulations goes up to

        Random rand = new Random();

        for (int count = 0; count <= candyAmt; count++) //starts at 0 candies and goes to candyAmt
        {
            deck = new Card[60]; //resets the deck each loop
            int randNum;
            int index = 0;

            do
            {
                randNum = rand.nextInt(deck.length);
                deck[randNum] = new Item();
                index++;
            }while (index < count && deck[randNum] instanceof Item); //puts the desired amount of rare candies into the deck

            for (int i = 0; i < deck.length; i++) //puts pokemon in the remaining spots of the deck
            {
                if (!(deck[i] instanceof Item))
                {
                    deck[i] = new Pokemon();
                }
            }

            int bricks = 0;

            for (int sim = 0; sim < sims; sim++) //main simulation loop
            {
                int candiesInPrize = 0;
                boolean noPokemon = true;

                do
                {
                    for (int i = 0; i < currHand.length; i++) //fills the player's hand with cards from the deck
                    {
                        currHand[i] = deck[rand.nextInt(deck.length)]; //selects a random card from the deck to put into the player's hand

                        if (currHand[i] instanceof Pokemon) //if there's a pokemon in the deck
                        {
                            noPokemon = false; //indicate there's a pokemon in the player's hand
                        }
                    }
                }while (noPokemon); //loop until theres at least 1 pokemon in the deck

                for (int i = 0; i < prizes.length; i++) //fills the prize pile
                { 
                    prizes[i] = deck[rand.nextInt(deck.length)];
                }

                for (int k = 0; k < prizes.length; k++) //checks how many candies are in the prize pile, if any
                {
                    if (prizes[k] instanceof Item) 
                    {
                        candiesInPrize++;
                    }
                }

                if (candiesInPrize == count) //if the amount of candies in the prize pile are equal to the total candies in the deck, that's a brick
                {
                    bricks++;
                }
            }
            double brickProb = ((double) bricks / sims) * 100; //brick percentage formula
            System.out.println("Candy Amount: " + count + "\nBrick Probability: " + brickProb + "%");
        }
    }
}
