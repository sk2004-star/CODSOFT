import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class StudentGradeCalculator {

    public static void main(String[] args) {
        Scanner interactionPortal = new Scanner(System.in);
        System.out.println("--- Student Grade Calculator ---");

        try {
            int quantityOfSubjects = retrievePositiveIntegerInput(interactionPortal, "Enter the number of subjects: ");

            if (quantityOfSubjects <= 0) {
                System.out.println("Error: Number of subjects must be positive.");
                return;
            }

            ArrayList<Integer> subjectMarkList = new ArrayList<>(quantityOfSubjects);
            int cumulativeMarkSummation = 0;
            final int MAXIMUM_MARK_PER_SUBJECT = 100;

            for (int subjectCounter = 1; subjectCounter <= quantityOfSubjects; subjectCounter++) {
                int validatedMarks = retrieveValidatedSubjectMarks(interactionPortal, subjectCounter, MAXIMUM_MARK_PER_SUBJECT);
                subjectMarkList.add(validatedMarks);
                cumulativeMarkSummation += validatedMarks;
            }

            double aggregateTotalPossibleMarks = (double) quantityOfSubjects * MAXIMUM_MARK_PER_SUBJECT;
            double calculatedAveragePercentage = (cumulativeMarkSummation / aggregateTotalPossibleMarks) * 100.0;

            String finalEvaluativeGrade = deduceFinalGrade(calculatedAveragePercentage);

            displayFormattedAcademicSummary(cumulativeMarkSummation, calculatedAveragePercentage, finalEvaluativeGrade);

        } catch (InputMismatchException ime) {
            System.out.println("Invalid input. Please enter valid numbers.");
        } catch (Exception unexpectedError) {
            System.out.println("An unexpected error occurred: " + unexpectedError.getMessage());
        } finally {
            interactionPortal.close();
        }
    }
    
    private static int retrievePositiveIntegerInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("Input must be a positive number (> 0).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid data type. Please enter a whole number.");
                scanner.next();
            }
        }
    }

    private static int retrieveValidatedSubjectMarks(Scanner scanner, int subjectIndex, int maxMarks) {
        while (true) {
            try {
                System.out.printf("Enter marks for Subject %d (Max %d marks): ", subjectIndex, maxMarks);
                int marksInput = scanner.nextInt();

                if (marksInput >= 0 && marksInput <= maxMarks) {
                    return marksInput;
                } else {
                    System.out.printf("Marks must be between 0 and %d.\n", maxMarks);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid data format. Please submit only numerical marks.");
                scanner.next();
            }
        }
    }

    private static String deduceFinalGrade(double percentageResult) {
        if (percentageResult >= 90.0) {
            return "A+ (Excellent)";
        } else if (percentageResult >= 80.0) {
            return "A (Very Good)";
        } else if (percentageResult >= 70.0) {
            return "B (Good)";
        } else if (percentageResult >= 60.0) {
            return "C (Satisfactory)";
        } else if (percentageResult >= 50.0) {
            return "D (Pass)";
        } else {
            return "F (Fail)";
        }
    }

    private static void displayFormattedAcademicSummary(int totalMarks, double averagePercentage, String grade) {
        System.out.println("\n=================================");
        System.out.println("         Results Summary         ");
        System.out.println("=================================");
        System.out.printf("Total Marks: %d\n", totalMarks);
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.printf("Grade: %s\n", grade);
        System.out.println("=================================");
    }
}
