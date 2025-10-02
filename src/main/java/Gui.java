import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {

    private TextField numberField = new TextField();
    private Label resultLabel = new Label();
    private Text text = new Text();
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();
    private double number;
    private double fahrenheit;
    private double celsius;

    @Override
    public void start(Stage stage) {
        numberField.setPromptText("Enter Value");
        text.setText("Select converter");
        ObservableList<String> options = FXCollections.observableArrayList("Fahrenheit to Celsius", "Celsius to Fahrenheit");

        choiceBox.setItems(options);


        Button convertButton = new Button("Convert");

        convertButton.setOnAction(e -> {
            try {
                number = Double.parseDouble(numberField.getText());
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid Value");
            }

            switch (choiceBox.getSelectionModel().getSelectedItem()) {
                case "Fahrenheit to Celsius":
                    fahrenheitToCelsius(number);
                    break;
                case "Celsius to Fahrenheit":
                    celsiusToFahrenheit(number);
                    break;
                default:
                    resultLabel.setText("Select Converter");


            }
        });

        Button saveButton = new Button("Save to DB");
        saveButton.setOnAction(e -> {
                    switch (choiceBox.getSelectionModel().getSelectedItem()) {
                        case "Fahrenheit to Celsius":
                            Database.saveTemperature(number, celsius, resultLabel);
                            break;
                        case "Celsius to Fahrenheit":
                            Database.saveTemperature(number, fahrenheit, resultLabel);
                            break;
                    }
                }
        );

        VBox root = new VBox(10, numberField, text, choiceBox, convertButton, resultLabel, saveButton);
        Scene scene = new Scene(root, 300, 200);

        stage.setTitle("Converter");
        stage.setScene(scene);
        stage.show();
    }

    private void celsiusToFahrenheit(double celsius) {
        fahrenheit = (celsius * 9 / 5) + 32;
        resultLabel.setText(String.format("Fahrenheit: %.2f", fahrenheit));
    }

    private void fahrenheitToCelsius(double fahrenheit) {
        celsius = (fahrenheit - 32) * 5 / 9;
        resultLabel.setText(String.format("Celsius: %.2f", celsius));

    }
}