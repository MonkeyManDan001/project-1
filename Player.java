import java.util.ArrayList;
import java.util.Random;

public class Player implements Game
{
    private ArrayList<Card> deck;
    private ArrayList<Card> currHand;
    private ArrayList<Card> prizes;
    private ArrayList<Card> bench;
    private Pokemon activePokemon;
    private GameManager game;

    public Player()
    {
        deck = new ArrayList<Card>();
        currHand = new ArrayList<Card>();
        prizes = new ArrayList<Card>();
        bench = new ArrayList<Card>();
        game = new GameManager();
    }
    
    public ArrayList<Card> getDeck()
    {
        return deck;
    }
    
    public ArrayList<Card> getCurrHand()
    {
        return currHand;
    }
    
    public ArrayList<Card> getBench()
    {
        return bench;
    }
    
    public ArrayList<Card> getPrizes()
    {
        return prizes;
    }
    
    public Pokemon getActivePokemon()
    {
        return activePokemon;
    }
    
    public void generateRandomDeck()
    {
        Random rand = new Random();
        
        for (int i = 0; i < 30; i++) //generates a certain amount of pokemon in the deck
        {
            ArrayList<Pokemon> pokemon = game.getPokemonList();
            
            deck.add(pokemon.get(rand.nextInt(pokemon.size())));
        }
        
        for (int i = 0; i < 25; i++) //generates a certain amount of energy in the deck
        {
            ArrayList<Energy> energy = game.getEnergyList();
            
            deck.add(energy.get(rand.nextInt(energy.size())));
        }
        
        for (int i = 0; i < 5; i++) //generates a certain amount of trainers in the deck
        {
            ArrayList<Trainer> trainer = game.getTrainerList();
            
            deck.add(trainer.get(rand.nextInt(trainer.size())));
        }
        
        for (int i = deck.size() - 1; i > 0; i--) //shuffles the deck
        {
            int index = rand.nextInt(i + 1);
            Card temp = deck.get(index);
            deck.set(index, deck.get(i));
            deck.set(i, temp);
        }
    }
    
    public void setCurrHand()
    {
        for (int i = 0; i < 7; i++) //gives 7 cards at the start of the game
        {
            currHand.add(deck.get(0));
            deck.remove(0);
        }
    }
    
    public void setBench()
    {
        for (int i = 0; i < currHand.size(); i++) //used to move pokemon from the players' hand to the deck
        {
            if (currHand.get(i) instanceof Pokemon && getBench().size() < 5) //makes sure that only pokemon are in the bench and only 5 at a time
            {
                bench.add(currHand.get(i));
                currHand.remove(i);
                i--;
            }
        }
    }
    
    public void setPrizes()
    {
        for (int i = 0; i < 6; i++) //sets aside 6 cards from the deck to go into the prize pile
        {
            prizes.add(deck.get(0));
            deck.remove(0);
        }
    }
    
    public void setActivePokemon(Card p)
    {
        activePokemon = (Pokemon)p;
    }
    
    public void reshuffleDeck() //reshuffles the deck after no pokemon were in the hand or bench of a player
    {
        Random rand = new Random();
        
        for (int i = 0; i < currHand.size(); i++) //puts the cards back into the deck
        {
            deck.add(currHand.get(i));
        }
        
        currHand.clear();
        
        for (int i = deck.size() - 1; i > 0; i--) //reshuffles the deck
        {
            int index = rand.nextInt(i + 1);
            Card temp = deck.get(index);
            deck.set(index, deck.get(i));
            deck.set(i, temp);
        }
    }
    
    public void drawCard()
    {
        if (deck.size() > 0) //draws a card from the deck
        {
            currHand.add(deck.get(0));
            deck.remove(0);
        }
    }
    
    public boolean pokemonInPlay() //checks if any pokemon are left in a deck, hand, or bench
    {
        boolean flag = false;
        
        for (int i = 0; i < deck.size(); i++)
        {
            if (deck.get(i) instanceof Pokemon)
            {
                flag = true;
            }
        }
        
        for (int i = 0; i < currHand.size(); i++)
        {
            if (currHand.get(i) instanceof Pokemon)
            {
                flag = true;
            }
        }
        
        for (int i = 0; i < bench.size(); i++)
        {
            if (bench.get(i) instanceof Pokemon)
            {
                flag = true;
            }
        }
        
        return flag;
    }
}
