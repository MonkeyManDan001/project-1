import java.util.ArrayList;
import java.util.Random;

public class PokemonGame
{
    public static void main(String[] args)
    {
        Player p1 = new Player();
        Player p2 = new Player();

        GameManager game = new GameManager();

        game.Start(p1, p2);
    }
}
