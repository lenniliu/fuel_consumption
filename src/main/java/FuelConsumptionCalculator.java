import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FuelConsumptionCalculator {
    private static final double FUEL_PRICE_PER_LITER = 1.67;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ResourceBundle messages = selectLanguage(scanner);

        System.out.println(messages.getString("prompt.distance"));
        double distance = getValidDouble(scanner, messages);

        System.out.println(messages.getString("prompt.fuel"));
        double fuel = getValidDouble(scanner, messages);

        double consumption = calculateFuelConsumption(distance, fuel);
        double cost = calculateFuelCost(fuel);

        System.out.println(MessageFormat.format(messages.getString("output.consumption"), String.format("%.2f", consumption)));
        System.out.println(MessageFormat.format(messages.getString("output.cost"), String.format("%.2f", cost)));
    }

    private static ResourceBundle selectLanguage(Scanner scanner) {
        System.out.println("Select language: 1 for English, 2 for French, 3 for Japanese, 4 for Persian");

        int choice = getValidInt(scanner);

        Locale locale;
        switch (choice) {
            case 1:  locale = new Locale("en", "US"); break;
            case 2:  locale = new Locale("fr", "FR"); break;
            case 3:  locale = new Locale("ja", "JP"); break;
            case 4:  locale = new Locale("fa", "IR"); break;
            default: locale = new Locale("en", "US"); break;
        }

        return ResourceBundle.getBundle("MessagesBundle", locale);
    }

    private static double getValidDouble(Scanner scanner, ResourceBundle messages) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("error.invalidInput"));
            }
        }
    }

    private static int getValidInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static double calculateFuelConsumption(double distance, double fuel) {
        return (fuel / distance) * 100;
    }

    private static double calculateFuelCost(double fuel) {
        return fuel * FUEL_PRICE_PER_LITER;
    }
}
