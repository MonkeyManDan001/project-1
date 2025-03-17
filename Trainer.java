public class Trainer extends Card
{   
    private String description;

    public Trainer(String n, String desc)
    {
        super.setName(n);
        description = desc;
    }

    public String getName()
    {
        return super.getName();
    } 

    public void setName(String n)
    {
        super.setName(n);
    }

    public void useAbility()
    {
        if (getName() == "Pokeball")
        {
            drawCard();
        }
    }
}
