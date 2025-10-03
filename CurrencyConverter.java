import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {

    private static final Map<String, Double> EXCHANGE_RATE_REGISTRY = new HashMap<>();
    private static final String BASE_CURRENCY_USD_CODE = "USD";

    static {
        EXCHANGE_RATE_REGISTRY.put(BASE_CURRENCY_USD_CODE, 1.0);
        EXCHANGE_RATE_REGISTRY.put("EUR", 0.9250);
        EXCHANGE_RATE_REGISTRY.put("GBP", 0.7930);
        EXCHANGE_RATE_REGISTRY.put("JPY", 156.40);
        EXCHANGE_RATE_REGISTRY.put("INR", 83.55);
        EXCHANGE_RATE_REGISTRY.put("CAD", 1.3710);
        EXCHANGE_RATE_REGISTRY.put("AUD", 1.5030);
    }

    public static void main(String[] args) {
        Scanner terminalInputInterface = new Scanner(System.in);
        System.out.println("--- Currency Converter Utility ---");
        
        displayAvailableCurrencyCodes();

        try {
            String sourceCurrencyDesignation = getValidatedCurrencyCode(terminalInputInterface, "Enter the Source Currency Code (e.g., USD, INR): ");
            String destinationCurrencyDesignation = getValidatedCurrencyCode(terminalInputInterface, "Enter the Target Currency Code (e.g., EUR, JPY): ");

            double numericalAmountToExchange = getValidatedAmount(terminalInputInterface, 
                                                                  String.format("Enter the amount in %s for conversion: ", sourceCurrencyDesignation));

            double resultingConvertedAmount = executeConversionLogic(numericalAmountToExchange, sourceCurrencyDesignation, destinationCurrencyDesignation);

            presentConversionOutcome(numericalAmountToExchange, sourceCurrencyDesignation, resultingConvertedAmount, destinationCurrencyDesignation);

        } catch (InputMismatchException ime) {
            System.out.println("Runtime Exception: The input provided for the amount was not in a valid numerical format.");
        } catch (Exception genericError) {
            System.out.println("A serious fault was detected: " + genericError.getMessage());
        } finally {
            terminalInputInterface.close();
        }
    }
    
    private static void displayAvailableCurrencyCodes() {
        System.out.print("Available Codes: ");
        EXCHANGE_RATE_REGISTRY.keySet().forEach(code -> System.out.print(code + "  |  "));
        System.out.println("\n----------------------------------");
    }

    private static String getValidatedCurrencyCode(Scanner scanner, String prompt) {
        String code = "";
        while (true) {
            System.out.print(prompt);
            code = scanner.next().trim().toUpperCase();
            if (EXCHANGE_RATE_REGISTRY.containsKey(code)) {
                return code;
            } else {
                System.out.println("Validation Failure: Code is not supported. Please try again.");
                displayAvailableCurrencyCodes();
            }
        }
    }

    private static double getValidatedAmount(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double amount = scanner.nextDouble();
                if (amount >= 0) {
                    return amount;
                } else {
                    System.out.println("Validation Failure: Amount must be non-negative.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Validation Failure: Input is not a number. Please enter a valid decimal value.");
                scanner.next();
            }
        }
    }

    private static double executeConversionLogic(double initialAmount, String sourceCode, String targetCode) {
        double sourceRateToUSD = EXCHANGE_RATE_REGISTRY.get(sourceCode);
        double targetRateToUSD = EXCHANGE_RATE_REGISTRY.get(targetCode);

        double amountNormalizedToUSD = initialAmount / sourceRateToUSD;

        double finalConvertedValue = amountNormalizedToUSD * targetRateToUSD;
        
        return Math.round(finalConvertedValue * 100.0) / 100.0;
    }

    private static void presentConversionOutcome(double initialAmount, String sourceCode, double finalAmount, String targetCode) {
        System.out.println("\n==================================");
        System.out.println("        Conversion Result         ");
        System.out.println("==================================");
        System.out.printf("%.2f %s is equal to %.2f %s\n", initialAmount, sourceCode, finalAmount, targetCode);
        System.out.println("==================================");
    }
}
