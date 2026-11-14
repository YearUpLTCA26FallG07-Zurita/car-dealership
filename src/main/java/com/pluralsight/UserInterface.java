package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class UserInterface {
    private Dealership dealership;

    public UserInterface() {

    }
    private void displayVehicles(List<Vehicle> allVehicles) {
        for (Vehicle vehicle : allVehicles) {
            System.out.println(vehicle);
        }
    }

    private void init() throws IOException {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
    }

    public void display() throws IOException {
        init();
        while (true) {
            init();
            System.out.println("""
                    Select an option:

                    1) Find vehicles by price range
                    2) Find vehicles by make and model
                    3) Find vehicles by year range
                    4) Find vehicles by color
                    5) Find vehicles by mile range
                    6) Find vehicles by type
                    -----------------------------------
                    7) List ALL vehicles
                    8) Add a vehicle
                    9) Remove a vehicle

                    99) Quit program""");

            int input = Console.promptForInt("Enter command: ");
            switch (input) {
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByVehicleTypeRequest();
                case 7 -> processGetAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 99 -> {return;}
                default -> System.out.println("Enter a valid input.");
            }
        }
    }

    private void processGetByPriceRequest() {
        BigDecimal minPrice = BigDecimal.valueOf(Console.promptForDouble("Enter lowest price: "));
        BigDecimal maxPrice = BigDecimal.valueOf(Console.promptForDouble("Enter highest price: "));

        System.out.println("""
                Select sorting order:
                
                1) Low to High
                2) High to Low
                """);

        int option = Console.promptForInt("Enter selection: ");
        boolean lowToHigh = true;

        switch (option) {
            case 1 -> lowToHigh = true;
            case 2 -> lowToHigh = false;
            case 99 -> {}
            default -> System.out.println("Invalid option. Enter a valid input.");
        }

        //todo add confirmation prompt

        displayVehicles(dealership.getVehiclesByPrice(minPrice, maxPrice, lowToHigh));
    }

    private void processGetByMakeModelRequest() {
        String make = Console.promptForString("Enter make of the vehicle: ");
        String model = Console.promptForString("Enter model of the vehicle: ");

        //todo add confirmation prompt

        displayVehicles(dealership.getVehiclesByMakeModel(make, model));

    }

    private void processGetByYearRequest() {
        int minYear = Console.promptForInt("Enter minimum year: ");
        int maxYear = Console.promptForInt("Enter maximum year: ");

        //todo add confirmation prompt

        displayVehicles(dealership.getVehiclesByYear(minYear, maxYear));
    }

    private void processGetByColorRequest() {
        String color = Console.promptForString("Enter color of vehicle: ").trim();

        //todo add confirmation prompt

        displayVehicles(dealership.getVehiclesByColor(color));
    }

    private void processGetByMileageRequest() {
        int minMileage = Console.promptForInt("Enter minimum mileage: ");
        int maxMileage = Console.promptForInt("Enter maximum mileage: ");

        //todo add confirmation prompt

        displayVehicles(dealership.getVehiclesByMileage(minMileage, maxMileage));
    }

    private void processGetByVehicleTypeRequest() {
        String vehicleType = Console.promptForString("Enter type of vehicle: ").trim();

        //todo add confirmation prompt
        //todo add extra text listing possible options (car, truck, SUV, van)

        displayVehicles(dealership.getVehiclesByType(vehicleType));
    }

    private void processGetAllVehiclesRequest() throws FileNotFoundException {
        displayVehicles(dealership.getAllVehicles());

    }

    private void processAddVehicleRequest() throws IOException {
        int vin = Console.promptForInt("Enter VIN number: ");
        int year = Console.promptForInt("Enter year of vehicle: ");
        String make = Console.promptForString("Enter vehicle make: ");
        String model = Console.promptForString("Enter vehicle model: ");
        String vehicleType = Console.promptForString("Enter vehicle type: ");
        String color = Console.promptForString("Enter vehicle color: ");
        int odometer = Console.promptForInt("Enter odometer reading: ");
        BigDecimal price = BigDecimal.valueOf(Console.promptForDouble("Enter vehicle price: "));

        Vehicle newCar = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        dealership.addVehicle(newCar);
        DealershipFileManager.saveDealership(dealership);
        System.out.println("Thank you.");

    }

    private void processRemoveVehicleRequest() throws IOException {
        int vehicleId = Console.promptForInt("Enter vehicle VIN number: ");

        Vehicle vehicleToRemove = null;

        for (Vehicle car : dealership.getAllVehicles()) {
            if (car.getVin() == vehicleId) {
                vehicleToRemove = car;
                break;
            }
        }
        if (vehicleToRemove != null) {
            dealership.removeVehicle(vehicleToRemove);
            DealershipFileManager.saveDealership(dealership);
        } else {
            System.out.println("Vehicle is not in inventory.");
        }
    }
}