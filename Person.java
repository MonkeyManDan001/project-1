import java.lang.Math;
import java.math.BigInteger;

public class Person
{
    private int birthdays;
    
    public Person(int b)
    {
        birthdays = b;
    }
    
    public int getBirthdays()
    {
        return birthdays;
    }
    
    public void setBirthdays(int b)
    {
        birthdays = b;
    }
    
    public BigInteger factorial(int num)
    {
        if (num == 0)
        {
            return BigInteger.valueOf(1);
        }
        
        BigInteger fact = BigInteger.valueOf(num);
        
        for (int i = num - 1; i > 0; i--)
        {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        
        return fact;
    }
    
    public BigInteger combination(int n, int r)
    {
        BigInteger comb = (factorial(n)).divide((factorial(r).multiply((factorial(n-r)))));
        
        return comb;
    }
    
    public double birthdayProb()
    {
        double num1 = combination(getBirthdays(), 2).doubleValue();
        double num2 = 364.0/365;
        double prob = 1 - Math.pow(num2, num1);
        
        return prob;
    }
}
