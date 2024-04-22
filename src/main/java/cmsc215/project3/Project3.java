// Developer: Alberth Matos
// Project Name: Project 3
// Class CMSC 215
// Date 23 April 2024
// Description: The program consists of two classes: Project3, and TripCost.
//
// The Project3 class is a JavaFX application that creates a window with text fields and combo boxes for the user
// to enter information about a trip. The user enters the distance of the trip, the cost of gasoline, the fuel
// efficiency of the vehicle, the number of days of the trip, the cost of the hotel, the cost of food, and the
// cost of attractions. The user can select the units for distance, gasoline cost, and fuel efficiency from
// combo boxes. The user clicks a button to calculate the total cost of the trip, which calls the TripCost
// class to calculate the cost of the trip. The user can enter only numbers in the text fields, and the
// program displays an error message if the user enters a non-numeric value. The program uses JavaFX for the
// GUI and BigDecimal for the calculations.

package cmsc215.project3;

// Import JavaFX classes
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Import plain java classes
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Project3 extends Application {
    // Initialize variables
    private final TextField distance = new TextField();
    private final TextField gasolineCost = new TextField();
    private final TextField fuelEfficiency = new TextField();
    private final TextField numberOfDays = new TextField();
    private final TextField hotelCost = new TextField();
    private final TextField foodCost = new TextField();
    private final TextField attractionCost = new TextField();
    private final TextField totalCost = new TextField();
    SimpleObjectProperty<ComboBox> distanceUnitComboBox = new SimpleObjectProperty<>(this, "distanceUnitComboBox");
    SimpleObjectProperty<ComboBox> gasolineCostUnitComboBox = new SimpleObjectProperty<>(this, "gasolineCostUnitComboBox");
    SimpleObjectProperty<ComboBox> fuelEfficiencyUnitComboBox = new SimpleObjectProperty<>(this, "fuelEfficiencyUnitComboBox");

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Define string constants
        String project3WindowTitle = "Trip Cost Estimator";
        String distanceLabel = "Distance:";
        String distanceUnitMiles = "miles";
        String distanceUnitKilometers = "kilometers";
        String gasCostLabel = "Gasoline Cost:";
        String gasCostUnitGallon = "dollars/gallon";
        String gasCostUnitLiter = "dollars/liter";
        String gasMileageLabel = "Gas Mileage:";
        String gasMileageUnitMpg = "miles/gallon";
        String gasMileageUnitKpl = "kilometers/liter";
        String numberOfDaysLabel = "Number of Days:";
        String hotelCostLabel = "Hotel Cost:";
        String foodCostLabel = "Food Cost:";
        String attractionCostLabel = "Attraction Cost:";
        String calculateButton = "Calculate";
        String totalCostLabel = "Total Cost:";
        // Define labels for combo boxes
        String[] distanceStrings = {distanceUnitMiles, distanceUnitKilometers};
        String[] gasolineCostStrings = {gasCostUnitGallon, gasCostUnitLiter};
        String[] fuelEfficiencyStrings = {gasMileageUnitMpg, gasMileageUnitKpl};

        // Create stage
        Stage newWindow = new Stage();
        newWindow.setTitle(project3WindowTitle);

        // Create grid pane to host window entries, with gaps of 10px all around, and a 10px horizontal and vertical gap
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);

        // Add nodes to pane
        // Distance Line
        pane.add(new Label(distanceLabel), 0, 0);
        pane.add(distance,1,0);
        distanceUnitComboBox.set(new ComboBox(FXCollections.observableArrayList(distanceStrings)));
        distanceUnitComboBox.get().setValue(distanceUnitMiles);
        distanceUnitComboBox.get().setMaxWidth(150);
        pane.add(distanceUnitComboBox.get(), 2, 0);

        // Gasoline Cost Line
        pane.add(new Label(gasCostLabel), 0, 1);
        pane.add(gasolineCost, 1, 1);
        gasolineCostUnitComboBox.set(new ComboBox(FXCollections.observableArrayList(gasolineCostStrings)));
        gasolineCostUnitComboBox.get().setValue(gasCostUnitGallon);
        gasolineCostUnitComboBox.get().setMaxWidth(150);
        pane.add(gasolineCostUnitComboBox.get(), 2, 1);

        // Gas Mileage Line
        pane.add(new Label(gasMileageLabel), 0, 2);
        pane.add(fuelEfficiency, 1, 2);
        fuelEfficiencyUnitComboBox.set(new ComboBox(FXCollections.observableArrayList(fuelEfficiencyStrings)));
        fuelEfficiencyUnitComboBox.get().setValue(gasMileageUnitMpg);
        fuelEfficiencyUnitComboBox.get().setMaxWidth(150);
        pane.add(fuelEfficiencyUnitComboBox.get(), 2, 2);

        // Number of Days Line
        pane.add(new Label(numberOfDaysLabel), 0, 3);
        pane.add(numberOfDays, 1, 3);

        // Hotel Cost Line
        pane.add(new Label(hotelCostLabel), 0, 4);
        pane.add(hotelCost, 1, 4);

        // Food Cost Line
        pane.add(new Label(foodCostLabel), 0, 5);
        pane.add(foodCost, 1, 5);

        // Attraction Cost Line
        pane.add(new Label(attractionCostLabel), 0, 6);
        pane.add(attractionCost, 1, 6);

        // Calculate Button
        Button calculate = new Button(calculateButton);
        calculate.setMaxWidth(200);
        calculate.setDefaultButton(true);
        pane.add(calculate, 1, 7);

        // Total Cost Line
        pane.add(new Label(totalCostLabel), 0, 8);
        totalCost.setEditable(false);
        pane.add(totalCost, 1, 8);

        // Calculate Button Event -- runs calculateTotalCost() when clicked
        calculate.setOnAction(e -> calculateTotalCost());

        // Create scene and set stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle(project3WindowTitle);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void calculateTotalCost() {
        // Calculate total cost of trip when the calculate button is clicked
        // Get values from fields
        Boolean proceed = true;
        double distanceValue = 0;
        double gasolineCostValue = 0;
        double gasMileageValue = 0;
        int numberOfDaysValue = 0;
        double hotelCostValue = 0;
        double foodCostValue = 0;
        double attractionsValue = 0;
        String distanceUnitValue = "";
        String gasolineCostUnitValue = "";
        String gasMileageUnitValue = "";
        String errorMessage = "Numbers only please.";

        try {
            distanceValue = Double.parseDouble(distance.getText());
        } catch (NumberFormatException e) {
            distance.setText(errorMessage);
            proceed = false;
        }

        try {
            gasolineCostValue = Double.parseDouble(gasolineCost.getText());
        } catch (NumberFormatException e) {
            gasolineCost.setText(errorMessage);
            proceed = false;
        }
        try {
            gasMileageValue = Double.parseDouble(fuelEfficiency.getText());
        } catch (NumberFormatException e) {
            fuelEfficiency.setText(errorMessage);
            proceed = false;
        }
        try {
            numberOfDaysValue = Integer.parseInt(numberOfDays.getText());
        } catch (NumberFormatException e) {
            numberOfDays.setText(errorMessage);
            proceed = false;
        }
        try {
            hotelCostValue = Double.parseDouble(hotelCost.getText());
        } catch (NumberFormatException e) {
            hotelCost.setText(errorMessage);
            proceed = false;
        }
        try {
            foodCostValue = Double.parseDouble(foodCost.getText());
        } catch (NumberFormatException e) {
            foodCost.setText(errorMessage);
            proceed = false;
        }
        try {
            attractionsValue = Double.parseDouble(attractionCost.getText());
        } catch (NumberFormatException e) {
            attractionCost.setText(errorMessage);
            proceed = false;
        }
        distanceUnitValue = distanceUnitComboBox.get().getValue().toString();
        gasolineCostUnitValue = gasolineCostUnitComboBox.get().getValue().toString();
        gasMileageUnitValue = fuelEfficiencyUnitComboBox.get().getValue().toString();

        if (proceed == true) {
            // Create trip object
            TripCost tripCost = new TripCost();

            // Calculate the gasoline cost of the trip
            BigDecimal gasolineCost = tripCost. getGasolineCost(distanceValue, distanceUnitValue, gasMileageValue, gasMileageUnitValue, gasolineCostValue, gasolineCostUnitValue);

            // Calculate the total cost of the trip
            BigDecimal totalCost = tripCost.calculateTotalTripCost(gasolineCost, hotelCostValue, foodCostValue, numberOfDaysValue, attractionsValue);

            // Round the total cost to 2 decimal places, rounding up if necessary.  Prefix the value with a "$"
            this.totalCost.setText("$" + totalCost.setScale(2, RoundingMode.UP));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
