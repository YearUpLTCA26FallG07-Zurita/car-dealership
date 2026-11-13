package com.pluralsight;

import com.sun.security.jgss.GSSUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentSkipListMap;

public class UserInterface {
    private Dealership dealership;

    public UserInterface() {

    }

    private void init() throws IOException {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
    }

    public void display() throws IOException {
        while (true) {
            init();
            System.out.println("""
                    Select an option:
                    
                    1) Find vehicles by price range
                    2) Find vehicles by make and model
                    3) Find vehicles by year range
                    4) Find vehciles by color
                    5) Find vehicles by mile range
                    6) Find vehicles by type
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

    }

    private void processGetByMakeModelRequest() {

    }

    private void processGetByYearRequest() {

    }

    private void processGetByColorRequest() {

    }

    private void processGetByMileageRequest() {

    }

    private void processGetByVehicleTypeRequest() {

    }

    private void processGetAllVehiclesRequest() throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(DealershipFileManager.fileName))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void processRemoveVehicleRequest() {

    }
}