import java.util.Random;
import java.util.Scanner;
import java.lang.Exception;

public class NumberGame {

    private static final int LOWER_BOUND_OF_RANGE = 1;
    private static final int UPPER_BOUND_OF_RANGE = 100;
    private static final int MAXIMUM_GUESSING_TRIES = 5;
    private static int overallCumulativeScore = 0;
    private static int RoundsSuccessfullyCompleted = 0;

    public static void main(String[] args) {
        Scanner userInteractionScanner = new Scanner(System.in);
        System.out.println("--- Welcome to the Number Guessing Game! ---");
        
        boolean continuePlayingRounds = true;
        
        while (continuePlayingRounds) {
            executeSingleGameRound(userInteractionScanner);
            System.out.print("\nDo you want to play another round? (yes/no): ");
            String playChoiceResponse = userInteractionScanner.next().trim().toLowerCase();
            
            if (!"yes".equals(playChoiceResponse)) {
                continuePlayingRounds = false;
            }
        }
        presentFinalPerformanceSummary();
        userInteractionScanner.close();
    }

    private static void executeSingleGameRound(Scanner inputSourceScanner) {
        Random numberGenerator = new Random();
        int targetIntegerForThisRound = numberGenerator.nextInt(UPPER_BOUND_OF_RANGE - LOWER_BOUND_OF_RANGE + 1) + LOWER_BOUND_OF_RANGE;
        int attemptsTakenInThisRound = 0;
        boolean secretNumberWasSuccessfullyGuessed = false;

        System.out.println("\n--- Starting New Round ---");
        System.out.printf("I have generated a number between %d and %d. You have %d attempts.\n", 
                          LOWER_BOUND_OF_RANGE, UPPER_BOUND_OF_RANGE, MAXIMUM_GUESSING_TRIES);

        while (attemptsTakenInThisRound < MAXIMUM_GUESSING_TRIES && !secretNumberWasSuccessfullyGuessed) {
            System.out.printf("Attempt %d/%d. Enter your guess: ", (attemptsTakenInThisRound + 1), MAXIMUM_GUESSING_TRIES);
            
            if (inputSourceScanner.hasNextInt()) {
                int participantGuessValue = inputSourceScanner.nextInt();
                attemptsTakenInThisRound++;

                if (participantGuessValue == targetIntegerForThisRound) {
                    secretNumberWasSuccessfullyGuessed = true;
                    int pointsEarned = MAXIMUM_GUESSING_TRIES - attemptsTakenInThisRound + 1;
                    overallCumulativeScore += pointsEarned;
                    RoundsSuccessfullyCompleted++;
                    
                    System.out.printf("Congratulations! You guessed the number %d correctly in %d attempts.\n", targetIntegerForThisRound, attemptsTakenInThisRound);
                    System.out.printf("You earned %d points this round.\n", pointsEarned);
                } else if (participantGuessValue < targetIntegerForThisRound) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                inputSourceScanner.next();
            }
        }

        if (!secretNumberWasSuccessfullyGuessed) {
            System.out.printf("You ran out of attempts. The number was %d.\n", targetIntegerForThisRound);
        }
    }

    private static void presentFinalPerformanceSummary() {
        System.out.println("\n=============================");
        System.out.println("      Game Over - Summary    ");
        System.out.println("=============================");
        System.out.printf("Rounds Won: %d\n", RoundsSuccessfullyCompleted);
        System.out.printf("Total Score: %d points\n", overallCumulativeScore);
        System.out.println("Thank you for playing!");
        System.out.println("=============================");
    }
}
