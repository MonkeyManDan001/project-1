public class Door
{
    private boolean ans;
    private int num;
    
    public Door(boolean answer, int number)
    {
        ans = answer;
        num = number;
    }
    
    public int getNumber()
    {
        return num;
    }
    
    public void setNumber(int newAns)
    {
        num = newAns;
    }
    
    public boolean getPrize()
    {
        return ans;
    }
    
    public void setPrize(boolean newAns)
    {
        ans = newAns;
    }
}
