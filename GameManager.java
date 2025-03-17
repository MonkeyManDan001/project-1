import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager
{   
    private static ArrayList<Pokemon> pokemonList;
    private static ArrayList<Energy> energyList;
    private static ArrayList<Trainer> trainerList;
    private ArrayList<Card> discardPile;

    public GameManager()
    {
        pokemonList = new ArrayList<Pokemon>();
        energyList = new ArrayList<Energy>();
        trainerList = new ArrayList<Trainer>();
        discardPile = new ArrayList<Card>();
    }

    public int choosePlayer(Player p1, Player p2)
    {
        Random rand = new Random();

        return rand.nextInt(2) + 1;
    }

    public void Start(Player p1, Player p2)
    {
        Scanner sc = new Scanner(System.in);

        selectablePokemon(); //loads in the pokemon
        selectableEnergy(); //loads in the energy
        selectableTrainers(); //loads in the trainers

        p1.generateRandomDeck(); //generates a random deck for both players
        p2.generateRandomDeck();

        p1.setCurrHand(); //assigns 7 cards to each players' hand
        p2.setCurrHand();

        boolean flag = false;

        do //checks for at least 1 pokemon in player 1's hand
        {
            for (int i = 0; i < p1.getCurrHand().size(); i++)
            {
                if (p1.getCurrHand().get(i) instanceof Pokemon)
                {
                    flag = true;
                }
            }

            if (flag == false) //if there's no pokemon in player 1's hand, they reshuffle and allow player 2 to draw a card
            {
                System.out.println("No pokemon in Player 1's hand, reshuffling...");
                p1.reshuffleDeck();
                p2.drawCard();
                p1.setCurrHand();
            }
        }while(flag == false);

        flag = false;

        do //checks for at least 1 pokemon in player 2's hand
        {
            for (int i = 0; i < p2.getCurrHand().size(); i++)
            {
                if (p2.getCurrHand().get(i) instanceof Pokemon)
                {
                    flag = true;
                }
            }

            if (flag == false) //if there's no pokemon in player 1's hand, they reshuffle and allow player 2 to draw a card
            {
                System.out.println("No Pokemon in Player 2's hand, reshuffling...");
                p2.reshuffleDeck();
                p1.drawCard();
            }
        }while(flag == false);

        p1.setBench(); //sets up the bench from the players' hands
        p2.setBench();

        p1.setPrizes(); //sets aside a prize pile from the deck
        p2.setPrizes();

        System.out.print("Cards in hand: \n"); 
        toString(p1.getCurrHand());
        System.out.print("Cards on the bench: \n");
        toString(p1.getBench());
        System.out.println("Which Pokemon would you like to use? (Enter the array index) ");

        int input = sc.nextInt(); //choose your starting pokemon

        while (input >= p1.getBench().size() || !(p1.getBench().get(input) instanceof Pokemon)) //checks to make sure you're picking a pokemon
        {
            System.out.println("That's not a Pokemon. Please choose again." );
            input = sc.nextInt();
        }

        p1.setActivePokemon(p1.getBench().get(input)); //sets the selected pokemon as "active"
        p1.getBench().remove(input); //removes it from the bench so there isnt a duplicate

        for (int i = 0; i < p2.getBench().size(); i++)
        {
            if (p2.getBench().get(i) instanceof Pokemon)
            {
                p2.setActivePokemon(p2.getBench().get(i));
                i = p2.getBench().size();
            }
        }

        int num = choosePlayer(p1, p2); //decides which player goes first

        while(p1.getPrizes().size() != 0 && p1.pokemonInPlay() && p1.getDeck().size() != 0 && p2.getPrizes().size() != 0 && p2.pokemonInPlay() && p2.getDeck().size() != 0) //main game loop that checks for win conditions
        {
            if (num == 1)
            {
                p1.setBench();

                if (p1.getActivePokemon().getHp() <= 0) //checks if the incoming active pokemon has fainted
                {
                    System.out.println(p1.getActivePokemon().getName() + " has fainted.");

                    discardPile.add(p1.getActivePokemon());
                    System.out.println("Your Pokemon has fainted. Choose another to battle with. ");

                    System.out.println("Cards in hand: ");
                    toString(p1.getCurrHand());
                    System.out.println("Cards on the bench: ");
                    toString(p1.getBench());

                    input = sc.nextInt();

                    while (input >= p1.getBench().size() || !(p1.getBench().get(input) instanceof Pokemon))
                    {
                        System.out.println("That's not a Pokemon. Please choose again." );
                        input = sc.nextInt();
                    }

                    p1.setActivePokemon(p1.getBench().get(input));

                    p1.setBench();
                }

                System.out.println("Player 1's Turn\n");

                do
                {
                    for (int i = 0; i < p1.getCurrHand().size(); i++)
                    {
                        if (p1.getCurrHand().get(i) instanceof Pokemon)
                        {
                            flag = true;
                        }
                    }

                    for (int i = 0; i < p1.getBench().size(); i++)
                    {
                        if (p1.getBench().get(i) instanceof Pokemon)
                        {
                            flag = true;
                        }
                    }

                    if (flag == false)
                    {
                        System.out.println("No pokemon in Player 1's hand, reshuffling...");
                        p1.reshuffleDeck();
                        p2.drawCard();
                    }
                }while(flag == false);

                p1.drawCard();

                System.out.println("Cards in hand: ");
                toString(p1.getCurrHand());
                System.out.println("Cards on the bench: ");
                toString(p1.getBench());
                System.out.println("Active Pokemon: " + p1.getActivePokemon().getName());

                input = 0;

                while(input != 3) //while input does not equal "Pass"
                {
                    System.out.println("What would you like to do?\n[0] Attack\n[1] Retreat\n[2] Use Trainer\n[3] Pass");

                    input = sc.nextInt();

                    if (input == 0) //attack
                    {    
                        int count = 0;

                        for (int i = 0; i < p1.getCurrHand().size(); i++)
                        {
                            if (p1.getCurrHand().get(i) instanceof Energy) //makes sure you have enough energy (2) to perform an attack
                            {
                                count++;
                            }
                        }

                        if (!(count >= 2))
                        {
                            System.out.println("You don't have enough energy");
                        }
                        else
                        {
                            for (int i = 0; i < p1.getActivePokemon().getAttacks().size(); i++) //displays the attack name and damage
                            {
                                System.out.println("[" + i + "] " + p1.getActivePokemon().getAttacks().get(i).getName() + ", " + p1.getActivePokemon().getAttacks().get(i).getDamage());
                            }

                            input = sc.nextInt();

                            while (input >= p1.getActivePokemon().getAttacks().size()) //checks to make sure you're picking an attack
                            {
                                System.out.println("That's not an attack");
                                input = sc.nextInt();
                            }

                            System.out.println(p1.getActivePokemon().getName() + " uses " + p1.getActivePokemon().getAttacks().get(input).getName() + ". It does " + p1.getActivePokemon().getAttacks().get(input).getDamage() + " damage.");
                            p2.getActivePokemon().setHp(p2.getActivePokemon().getHp() - p1.getActivePokemon().getAttacks().get(input).getDamage()); //displays the attack and takes the health from the opposing pokemon

                            if (p2.getActivePokemon().getHp() < 0) //stops negative health from appearing
                            {
                                System.out.println("Player 2's Pokemon's Current Health: 0");
                            }
                            else //displays health after the attack
                            {
                                System.out.println("Player 2's Pokemon's Current Health: " + p2.getActivePokemon().getHp());
                            }
                            
                            num = 2; //switches turns
                            break;
                        }
                    }
                    if (input == 1) //retreat
                    {
                        int count = 0;

                        for (int i = 0; i < p1.getCurrHand().size(); i++)
                        {
                            if (p1.getCurrHand().get(i) instanceof Energy) //checks if you have enough energy to retreat
                            {
                                count++;
                            }
                        }

                        if (!(count >= 2))
                        {
                            System.out.println("You don't have enough energy");
                        }
                        else
                        {
                            System.out.println("You retreated. Please choose another Pokemon to battle.");
                            count = 0;
                            int i = 0;

                            p1.getCurrHand().add(p1.getActivePokemon());

                            while(count < 2) //discards energy used to retreat
                            {
                                if (p1.getCurrHand().get(i) instanceof Energy)
                                {
                                    discardPile.add(p1.getCurrHand().get(i));
                                    p1.getCurrHand().remove(i);
                                    i--;
                                    count++;
                                }
                                i++;
                            }

                            System.out.println("Cards in hand: ");
                            toString(p1.getCurrHand());
                            System.out.println("Cards on the bench: ");
                            toString(p1.getBench());

                            input = sc.nextInt();

                            while (input >= p1.getBench().size() || !(p1.getBench().get(input) instanceof Pokemon)) //checks that you're picking an actual pokemon
                            {
                                System.out.println("That's not a Pokemon. Please choose again." );
                                input = sc.nextInt();
                            }

                            p1.setActivePokemon(p1.getBench().get(input)); //changes the active pokemon to the new one

                            p1.setBench();
                            
                            num = 2; //switches turns
                        }
                    }

                    if (input == 2) //use a trainer card
                    {
                        boolean indicator = false;

                        for (int i = 0; i < p1.getCurrHand().size(); i++)
                        {
                            if (p1.getCurrHand().get(i) instanceof Trainer) //checks for trainer cards
                            {
                                indicator = true;
                            }
                        }

                        if (indicator == false)
                        {
                            System.out.println("You don't have a trainer");
                        }
                        else
                        {
                            for (int i = 0; i < p1.getCurrHand().size(); i++)
                            {
                                if (p1.getCurrHand().get(i) instanceof Trainer) //applies the trainer card, removes it from your hand, and moves it to the discard pile
                                {
                                    p1.drawCard();
                                    discardPile.add(p1.getCurrHand().get(i));
                                    p1.getCurrHand().remove(i);
                                    System.out.println("Player 1 used a trainer, drawing 1 card.");
                                    i = p1.getCurrHand().size();
                                }
                            }

                            System.out.println("Cards in hand: ");
                            toString(p1.getCurrHand());
                            System.out.println("Cards on the bench: ");
                            toString(p1.getBench());
                        }
                    }

                    if (input == 3) //pass
                    {
                        num = 2; //switches turns
                    }
                }
            }

            if (num == 2) //player 2
            {
                if (p2.getActivePokemon().getHp() <= 0) //checks for a fainted active pokemon
                {
                    System.out.println(p2.getActivePokemon().getName() + " has fainted.");
                    discardPile.add(p2.getActivePokemon());

                    Random rand = new Random();

                    if (p2.getBench().size() <= 0) //make sure the bench isnt empty
                    {
                        break;
                    }
                    int random = rand.nextInt(p2.getBench().size()) + 1;

                    while (random >= p2.getBench().size() || !(p2.getBench().get(random) instanceof Pokemon)) //picks a random pokemon from the bench to be the new active pokemon
                    {
                        random = rand.nextInt(p2.getBench().size());
                    }

                    p2.setActivePokemon(p2.getBench().get(random)); //sets new active pokemon
                    p2.getBench().remove(random); //removes the active pokemon from the bench

                    p2.setBench(); //moves any pokemon in player 2's hand into the bench
                }

                System.out.println("Player 2's Turn\n");

                toString(p2.getBench());
                System.out.println("Player 2's active Pokemon: " + p2.getActivePokemon().getName());

                flag = false;

                do //checks for pokemon in your hand or bench
                {
                    for (int i = 0; i < p2.getCurrHand().size(); i++)
                    {
                        if (p2.getCurrHand().get(i) instanceof Pokemon)
                        {
                            flag = true;
                        }
                    }

                    for (int i = 0; i < p2.getBench().size(); i++)
                    {
                        if (p2.getBench().get(i) instanceof Pokemon)
                        {
                            flag = true;
                        }
                    }

                    if (flag == false) //reshuffles if no pokemon are found
                    {
                        System.out.println("No Pokemon in Player 2's hand, reshuffling...");
                        p2.reshuffleDeck();
                        p1.drawCard();
                        p2.setCurrHand();
                    }
                }while(flag == false);

                int count = 0;

                for (int i = 0; i < p2.getCurrHand().size(); i++)
                {
                    if (p2.getCurrHand().get(i) instanceof Energy) //checks energy for an attack
                    {
                        count++;
                    }

                    if (p2.getCurrHand().get(i) instanceof Trainer) //checks for trainers to use
                    {
                        p2.getCurrHand().get(i).drawCard();
                        discardPile.add(p2.getCurrHand().get(i));
                        p2.getCurrHand().remove(i);
                        System.out.println("Player 2 used a trainer, drawing 1 card.");
                    }
                }

                if(count == 2) //if there's enough energy, player 2 attacks
                {
                    System.out.println(p2.getActivePokemon().getName() + " uses " + p2.getActivePokemon().getAttacks().get(0).getName() + ". It does " + p2.getActivePokemon().getAttacks().get(0).getDamage() + " damage.");
                    p1.getActivePokemon().setHp(p1.getActivePokemon().getHp() - p2.getActivePokemon().getAttacks().get(0).getDamage());

                    System.out.println("Player 1's Pokemon's Current Health: " + p1.getActivePokemon().getHp());
                    count = 0;
                }

                num = 1; //switches turns

            }
        }

        if (p1.getPrizes().size() == 0 || !p1.pokemonInPlay() || p1.getDeck().size() == 0 || p1.getBench().size() <= 0) //checks for who wins
        {
            System.out.println("Player 2 Wins");
        }

        if (p2.getPrizes().size() == 0 || !p2.pokemonInPlay() || p2.getDeck().size() == 0 || p2.getBench().size() <= 0)
        {
            System.out.println("Player 1 Wins");
        }
    }

    public void selectablePokemon() //all pokemon in the game
    {
        Pokemon bulbasaur = new Pokemon("Bulbasaur");
        bulbasaur.setHp(45);
        bulbasaur.setAttack("Vine Whip", 45);

        Pokemon heatmor = new Pokemon("Heatmor");
        heatmor.setHp(85);
        heatmor.setAttack("Lick", 30);

        Pokemon psyduck = new Pokemon("Psyduck");
        psyduck.setHp(50);
        psyduck.setAttack("Scratch", 40);

        Pokemon helioptile = new Pokemon("Helioptile");
        helioptile.setHp(44);
        helioptile.setAttack("Mud Slap", 20);

        pokemonList.add(bulbasaur);
        pokemonList.add(heatmor);
        pokemonList.add(psyduck);
        pokemonList.add(helioptile);
    }

    public void selectableEnergy() //all energy in the game
    {
        Energy colorless = new Energy("Colorless");

        energyList.add(colorless);
    }

    public void selectableTrainers() //all trainers in the game
    {
        Trainer pokeBall = new Trainer("Pokeball", "Put a random Basic Pokemon from your deck into your hand");

        trainerList.add(pokeBall);
    }

    public void setDiscardPile(Card card)
    {
        discardPile.add(card);
    }

    public ArrayList<Pokemon> getPokemonList()
    {
        return pokemonList;
    }

    public ArrayList<Energy> getEnergyList()
    {
        return energyList;
    }

    public ArrayList<Trainer> getTrainerList()
    {
        return trainerList;
    }

    public ArrayList<Card> getDiscardPile()
    {
        return discardPile;
    }

    public String toString(ArrayList<Card> p) //toString method to see the outputs of currHand and benches more easily
    {
        for (int i = 0; i < p.size(); i++)
        {
            System.out.println("[" + i + "] " + p.get(i).getName());
        }

        return "";
    }
}
