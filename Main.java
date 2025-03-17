import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("How many people are in your group? ");
        int num = scanner.nextInt();
        
        Person birthdays = new Person(num);
        System.out.println("People in group: " + num + "\nProbability of 2 people having the same birthday: " + birthdays.birthdayProb());
    }
}
