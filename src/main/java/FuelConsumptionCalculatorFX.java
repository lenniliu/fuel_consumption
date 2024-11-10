import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class FuelConsumptionCalculatorFX extends Application {

    private static final double FUEL_PRICE_PER_LITER = 1.67;
    private ResourceBundle messages;
    private Locale currentLocale;

    private TextField distanceField;
    private TextField fuelField;
    private Label consumptionLabel;
    private Label costLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lenni Liukkonen");

        // Default locale is English
        setLocale(new Locale("en", "US"));

        // UI Components
        Label distanceLabel = new Label(messages.getString("prompt.distance"));
        distanceField = new TextField();

        Label fuelLabel = new Label(messages.getString("prompt.fuel"));
        fuelField = new TextField();

        Button calculateButton = new Button(messages.getString("calculate.button"));
        calculateButton.setOnAction(e -> calculateFuelConsumption());

        consumptionLabel = new Label();
        costLabel = new Label();

        // Language buttons
        Button enButton = new Button("EN");
        enButton.setOnAction(e -> setLocale(new Locale("en", "US")));

        Button frButton = new Button("FR");
        frButton.setOnAction(e -> setLocale(new Locale("fr", "FR")));

        Button jpButton = new Button("JP");
        jpButton.setOnAction(e -> setLocale(new Locale("ja", "JP")));

        Button irButton = new Button("IR");
        irButton.setOnAction(e -> setLocale(new Locale("fa", "IR")));

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15, 15, 15, 15));

        HBox languageButtons = new HBox(10, enButton, frButton, jpButton, irButton);
        languageButtons.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().addAll(
                distanceLabel, distanceField,
                fuelLabel, fuelField,
                calculateButton, consumptionLabel, costLabel,
                languageButtons
        );

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private void updateTexts() {
        if (distanceField != null && distanceField.getParent() != null) {
            ((Label) distanceField.getParent().getChildrenUnmodifiable().get(0)).setText(messages.getString("prompt.distance"));
        }
        if (fuelField != null && fuelField.getParent() != null) {
            ((Label) fuelField.getParent().getChildrenUnmodifiable().get(2)).setText(messages.getString("prompt.fuel"));
        }
    }
    private void setLocale(Locale locale) {
        currentLocale = locale;
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
        updateTexts();
    }



    private void calculateFuelConsumption() {
        try {
            double distance = Double.parseDouble(distanceField.getText());
            double fuel = Double.parseDouble(fuelField.getText());

            double consumption = (fuel / distance) * 100;
            double cost = fuel * FUEL_PRICE_PER_LITER;

            consumptionLabel.setText(MessageFormat.format(messages.getString("output.consumption"), String.format("%.2f", consumption)));
            costLabel.setText(MessageFormat.format(messages.getString("output.cost"), String.format("%.2f", cost)));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messages.getString("error.invalidInput"));
            alert.setContentText(messages.getString("error.invalidInput"));
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

