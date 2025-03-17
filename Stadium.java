public class Stadium
{
    public void battle(Pokemon p1, Pokemon p2)
    {
        int turn;
        
        if (p1.getSpeed() > p2.getSpeed() || p1.getSpeed() == p2.getSpeed()) //whichever pokemon has more speed goes first
        {
            turn = 1;
        }
        else 
        {
            turn = 2;
        }
        
        while (p1.getHp() > 0 && p2.getHp() > 0) //continues the loop until one of the pokemon has 0 health or less (i.e. knocked out)
        {
            if (turn == 1)
            {
                p2.setHp(p2.getHp() - p1.getAttack()); //subtracts the damage done from the attack from the pokemons health
                if (p2.getHp() < 0)
                {
                    System.out.println("P1 attacks P2! Current health of P2: 0"); //put in case of negative health
                }
                else
                {
                    System.out.println("P1 attacks P2! Current health of P2: " + p2.getHp()); //displays the current health of the pokemon after the attack
                }
                turn = 2; //switches turns
            }
            else if (turn == 2)
            {
                p1.setHp(p1.getHp() - p2.getAttack()); //subtracts the damage done from the attack from the pokemons health
                if (p1.getHp() < 0)
                {
                    System.out.println("P2 attacks P1! Current health of P1: 0"); //put in case of negative health
                }
                else
                {
                    System.out.println("P2 attacks P1! Current health of P1: " + p1.getHp()); //displays the current health of the pokemon after the attack
                }
                turn = 1; //switches turns
            }
        }
        
        if (p1.getHp() <= 0) //if player 1 is knocked out
        {
            System.out.println("P1 is knocked out! P2 wins!"); //declares player 2 as the winner
        }
        
        if (p2.getHp() <= 0) //if player 2 is knocked out
        {
            System.out.println("P2 is knocked out! P1 wins!"); //declares player 1 as the winner
        }
    }
}
