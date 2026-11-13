package com.pluralsight;

import java.io.*;
import java.math.BigDecimal;

//todo fix filewriter to properly list dealer info at top, currently says "null | null | null".

public class DealershipFileManager {
    static String fileName = "C:\\Users\\zurit\\pluralsight\\workshop\\car-dealership\\src\\main\\java\\com\\pluralsight\\inventory.csv\\";
    public static Dealership getDealership() throws IOException {

        Dealership dealer;
        try (BufferedReader readFile = new BufferedReader(new FileReader(fileName))) {
            String line = readFile.readLine();
            String[] dealerInfo = line.split("\\|");
            dealer = new Dealership(dealerInfo[0], dealerInfo[1], dealerInfo[2]);

            while ((line = readFile.readLine()) != null) {
                String[] listing = line.split("\\|");

                Vehicle newVehicle = new Vehicle(
                        Integer.parseInt(listing[0].trim()),
                        Integer.parseInt(listing[1].trim()),
                        listing[2],
                        listing[3],
                        listing[4],
                        listing[5],
                        Integer.parseInt(listing[6].trim()),
                        new BigDecimal(listing[7].trim()));

                dealer.addVehicle(newVehicle);
            }
        }
        return dealer;
    }


    public static void saveDealership(Dealership dealership) throws IOException {
        try (BufferedWriter writeFile = new BufferedWriter(new FileWriter(fileName))){
            writeFile.write(dealership.getName() + " | " + dealership.getAddress() + " | " + dealership.getPhone());
            writeFile.newLine();

            for (Vehicle cars : dealership.getAllVehicles()) {
                writeFile.write(cars.getVin() + "|" + cars.getYear() + "|" + cars.getMake() + "|" +
                        cars.getModel() + "|" + cars.getVehicleType() + "|" + cars.getColor() + "|" +
                        cars.getOdometer() + "|" + cars.getPrice());
                writeFile.newLine();
            }
        } catch (IOException e) {
            System.out.println("There was an error.");;
        }

    }
}

