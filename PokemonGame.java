import java.util.Random;

public class PokemonGame
{
    public static void main(String[] args)
    {   
        Card[] deck = new Card[60]; //declares a deck of 60 cards
        Card[] currHand = new Card[7]; //declares a players hand of 7 cards
        int sims = 10000;

        Random rand = new Random();
        
        for (int count = 1; count <= deck.length; count++) //the count represents the number of pokemon in the deck
        {
            int randNum = rand.nextInt(deck.length); //picks a random index in the deck array
            int index = 0;

            do
            {
                deck[randNum] = new Pokemon();
                index++;
            }while (index < count && deck[randNum] instanceof Pokemon); //puts the amount of pokemon in the deck depending on the count of pokemon
                                                                        //this allows the code to test from 1 card in the deck to 60 
            for (int i = 0; i < deck.length; i++) //fills the rest of the deck with energy cards
            {
                if (!(deck[i] instanceof Pokemon))
                {
                    deck[i] = new Energy();
                }
            }

            int mullCount = 0;

            for (int sim = 0; sim < sims; sim++) //main loop running the monte carlo experiment
            {
                int pokemonInHand = 0;

                for (int card = 0; card < currHand.length; card++) //selects 7 cards from the deck, checking if any of them are pokemon
                { 
                    if (deck[rand.nextInt(deck.length)] instanceof Pokemon) 
                    {   
                        pokemonInHand++; //adds to the counter when a pokemon is selected
                    }
                }

                if (pokemonInHand == 0) //if there are no pokemon in the player's hand
                {
                    mullCount++; //then the mulligan counter goes up
                }
            }
            double mullProb = ((double) mullCount / sims) * 100; //finds the percentage of a mulligan during each experiment
            System.out.println("Number of Cards: " + count + "\nMulligan Probability: " + mullProb + "%");
        }
    }
}
