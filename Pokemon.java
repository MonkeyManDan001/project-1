import java.util.ArrayList;

public class Pokemon extends Card
{
    private int hp;
    private ArrayList<Attack> attacks = new ArrayList<Attack>();
    
    public Pokemon(String n)
    {
        setName(n);
    }
    
    public String getName()
    {
        return super.getName();
    }
    
    public int getHp()
    {
        return hp;
    }
    
    public ArrayList<Attack> getAttacks()
    {
        return attacks;
    }
    
    public void setName(String n)
    {
        super.setName(n);
    }
    
    public void setHp(int h)
    {
        hp = h;
    }
    
    public void setAttack(String name, int damage)
    {
        attacks.add(new Attack(name, damage));
    }
}