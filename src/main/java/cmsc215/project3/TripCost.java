// Developer: Alberth Matos
// Project Name: Project 3
// Class CMSC 215
// Date 23 April 2024
// Description: The program consists of two classes: Project3, and TripCost.
//
// The TripCost class contains methods to calculate the gasoline cost of the trip and the total cost of the trip.
// The gasoline cost is calculated by converting the distance to miles and the fuel efficiency to miles per gallon
// if necessary, and then multiplying the distance by the fuel efficiency and the cost of gasoline. The total cost
// of the trip is calculated by adding the gasoline cost, the cost of the hotel, the cost of food, and the cost of
// attractions. The total cost is rounded to two decimal places and returned to the calling method.

package cmsc215.project3;

import java.math.BigDecimal;

final public class TripCost {
    private final double distance;
    private final double gasolineCost;
    private final double gasMileage;
    private final int numberOfDays;
    private final double hotelCost;
    private final double foodCost;
    private final double attractions;
    private final String distanceUnit;
    private final String gasolineCostUnit;
    private final String gasMileageUnit;

    public TripCost(double distance, double gasolineCost, float gasMileage, int numberOfDays, double hotelCost,
                    double foodCost, double attractions, String distanceUnit, String gasolineCostUnit,
                    String gasMileageUnit) {
        this.distance = distance;
        this.gasolineCost = gasolineCost;
        this.gasMileage = gasMileage;
        this.numberOfDays = numberOfDays;
        this.hotelCost = hotelCost;
        this.foodCost = foodCost;
        this.attractions = attractions;
        this.distanceUnit = distanceUnit;
        this.gasolineCostUnit = gasolineCostUnit;
        this.gasMileageUnit = gasMileageUnit;
    }

    public TripCost() {
        this.distance = 0;
        this.gasolineCost = 0;
        this.gasMileage = 0;
        this.numberOfDays = 0;
        this.hotelCost = 0;
        this.foodCost = 0;
        this.attractions = 0;
        this.distanceUnit = "";
        this.gasolineCostUnit = "";
        this.gasMileageUnit = "";
    }

    public BigDecimal getGasolineCost(double distance, String distanceUnit,
                                      double gasMileage, String gasMileageUnit,
                                      double gasolineCost, String gasCostUnit) {
        // getGasolineCost returns a BigDecimal to avoid floating point errors.
        //
        // Initialize variables to 0 in case one of the unit conversions fails due to  invalid input.
        // (which shouldn't happen, since those values aren't used-entered)
        double distanceInMiles = 0;
        double gasInGallons = 0;
        double gasMpG = 0;

        // Convert all units to miles, gallons, and dollars/gallon up front, and perform calculations based on those
        // units.  This will simplify the code and make it easier to understand.

        if (distanceUnit.equals("miles")) {
            // If units are already in miles, no conversion is necessary.
            distanceInMiles = distance;
        } else if (distanceUnit.equals("kilometers")) {
            // otherwise, convert from km to miles.
            distanceInMiles = convertKilometersToMiles(distance);
        }

        if (gasCostUnit.equals("dollars/gallon")) {
            // If units are already in dollars/gallon, no conversion is necessary.
            gasMpG = gasolineCost;
        } else if (gasCostUnit.equals("dollars/liter")) {
            // otherwise, convert from dollars/liter to dollars/gallon.
            gasMpG = convertLiterToGallon(gasolineCost);
        }

        if (gasMileageUnit.equals("miles/gallon")) {
            // If units are already in miles/gallon, no conversion is necessary.
            gasInGallons = gasMileage;
        } else if (gasMileageUnit.equals("kilometers/liter")) {
            // otherwise, convert from km/L to miles/gallon.
            gasInGallons = convertKpLToMpG(gasMileage);
        }
        double sumnum = calculateGasolineCost(distanceInMiles, gasInGallons, gasMpG);
        return BigDecimal.valueOf(calculateGasolineCost(distanceInMiles, gasInGallons, gasMpG));
    }

    private double calculateGasolineCost(double distanceInMiles, double gasMileage, double gasolineCost) {
        // Cost is equal to the distance divided by the gas mileage, times the cost of gasoline.
        return ((distanceInMiles / gasMileage) * gasolineCost);
    }

    public BigDecimal calculateTotalTripCost(BigDecimal gasolineCost, double hotelCost, double foodCost,
                                             int numberOfDays, double attractions) {
        // calculateTotalTripCost returns a BigDecimal to avoid floating point errors.
        //
        // Cost is equal to the sum of hotel and food costs per day, times the number of days, plus the total
        // attraction cost, plus the total gasoline cost.
        return gasolineCost.add(BigDecimal.valueOf(((hotelCost + foodCost) * numberOfDays) + attractions));
    }

    private double convertKilometersToMiles(double kilometers) {
        // 1 mile = 1.60934 km
        return (kilometers / 1.60934);
    }

    private double convertLiterToGallon(double costPerLiter) {
        // 1 gallon = 3.78541 liters
        return (costPerLiter / 3.78541);
    }

    private double convertKpLToMpG(double kilometersPerLiter) {
        // Convert km/L to miles/gallon by dividing by the conversion factor.
        // n.b. 1 mile = 1.60934 km, 1 gallon = 3.78541 liters, 1mpg = 0.425144 kpl
        return (kilometersPerLiter / (convertKilometersToMiles(1)/convertLiterToGallon(1)));
    }
}
