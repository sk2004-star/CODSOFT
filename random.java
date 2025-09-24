import java.util.Random;

public class random {
    public static void main(String[] args) {
        double val1 = 1;
        double val2 = 100;

        Random rand = new Random();
        double randNumber = rand.nextDouble((val2 - val1) + 1) + val1;

        System.out.println("Generated random number between 1 & 100: " + randNumber);
    }
}