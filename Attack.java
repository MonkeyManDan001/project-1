public class Attack
{
    private String name;
    private int damage;

    public Attack(String n, int d)
    {
        name = n;
        damage = d;
    }
    
    public int getDamage()
    {
        return damage;
    }
    
    public void setDamage(int d)
    {
        damage = d;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String n)
    {
        name = n;
    }
}
