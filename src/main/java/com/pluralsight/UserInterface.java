package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class UserInterface {
    private Dealership dealership;
    private boolean running = true;

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
                case 99 -> {
                    return;
                }
                default -> System.out.println("Enter a valid input.");
            }
        }
    }

    private void processGetByPriceRequest() {
        String inputLow = "";
        String inputHigh = "";
        BigDecimal minPrice;
        BigDecimal maxPrice;
        running = true;
        while (running) {
            //Loop that performs exception handling for LOWEST mileage; prompts again if triggered.
            while (true) {
                try {
                    inputLow = Console.promptForString("Enter lowest price range, or leave blank for inventory minimum: ").trim();
                    minPrice = inputLow.isEmpty() ? new BigDecimal(0) : new BigDecimal(inputLow);

                    BigDecimal minAllowed = new BigDecimal("5000");

                    if (minPrice.compareTo(minAllowed) < 0) {
                        throw new IllegalArgumentException("Lowest price must be greater than $5,000.00.");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            //Loop that performs exception handling for HIGHEST mileage; prompts again if triggered.
            while (true) {
                try {
                    inputHigh = Console.promptForString("Enter highest price range, or leave blank for inventory maximum: ").trim();
                    maxPrice = inputHigh.isEmpty() ? new BigDecimal(0) : new BigDecimal(inputLow);

                    BigDecimal maxAllowed = new BigDecimal("40001");

                    if (maxPrice.compareTo(maxAllowed) >= 0) {
                        throw new IllegalArgumentException("Highest price must be less than $40,000.00.");
                    }
                    if (maxPrice.compareTo(minPrice) < 0) {
                        throw new IllegalArgumentException("Maximum price cannot be less than minimum price.");
                    }
                    if (inputLow.isEmpty() && inputHigh.isEmpty()) {
                        throw new IllegalArgumentException("Both fields cannot be empty.");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("""
                    Select sorting order:
                    
                    1) Low to High
                    2) High to Low
                    """);

            boolean lowToHigh = processSetSortingOrder();
            String sortOrder = lowToHigh ? "Lowest to highest" : "Highest to lowest";

            System.out.printf("""
                    You entered:
                    
                    Range: $%,.2f - $%,.2f
                    Sort : %s
                    
                    Is this correct?
                    
                    1) Yes.
                    2) No, revise input.
                    """, minPrice, maxPrice, sortOrder);

            int confirm = Console.promptForInt("Enter selection: ");
            switch (confirm) {
                case 1 -> {
                    displayVehicles(dealership.getVehiclesByPrice(minPrice, maxPrice, lowToHigh));
                    running = false;
                }
                case 2 -> running = true;
                default -> System.out.println("Enter a valid input.");
            }
        }
    }

    private void processGetByMakeModelRequest() {
        running = true;
        while (running) {
            String make = Console.promptForString("Enter make of the vehicle: ");
            String model = Console.promptForString("Enter model of the vehicle: ");

            System.out.println("""
                    Select alphabetical sorting order:
                    
                    1) A - Z
                    2) Z - A
                    """);

            boolean lowToHigh = processSetSortingOrder();
            String sortOrder = lowToHigh ? "A - Z" : "Z - A";

            System.out.printf("""
                    You entered:
                    
                    Vehicle: %s %s
                    Sort   : %s
                    
                    Is this correct?
                    
                    1) Yes.
                    2) No, revise input.
                    """, make, model, sortOrder);

            int confirm = Console.promptForInt("Enter selection: ");
            switch (confirm) {
                case 1 -> {
                    displayVehicles(dealership.getVehiclesByMakeModel(make, model, lowToHigh));
                    running = false;
                }
                case 2 -> running = true;
                default -> System.out.println("Enter a valid input.");
            }
        }
    }

    private void processGetByYearRequest() {
        boolean running = true;
        System.out.println("""
                What type of range would you like to view?
                
                1) Single year range
                2) Multiple year range
                """);

        int range = Console.promptForInt("Enter selection: ");

        while (running) {
            switch (range) {
                case 1 -> {
                    int year = Console.promptForInt("Enter year: ");

                    System.out.printf("""
                            You entered:
                            
                            Year: %d
                            
                            Is this correct?
                            
                            1) Yes.
                            2) No, revise input.
                            """, year);

                    int confirm = Console.promptForInt("Enter selection: ");

                    switch (confirm) {
                        case 1 -> {
                            displayVehicles(dealership.getVehiclesBySingleYear(year));
                            running = false;
                        }
                        case 2 -> running = true;
                        default -> System.out.println("Enter a valid input.");
                    }
                }
                case 2 -> {
                    int minYear = Console.promptForInt("Enter minimum year: ");
                    int maxYear = Console.promptForInt("Enter maximum year: ");

                    System.out.println("""
                            Select sorting order:
                            
                            1) Oldest to newest
                            2) Newest to oldest
                            """);

                    boolean lowToHigh = processSetSortingOrder();
                    String sortOrder = lowToHigh ? "Oldest to newest" : "Newest to oldest";

                    System.out.printf("""
                            You entered:
                            
                            Vehicle: %d %d
                            Sort   : %s
                            
                            Is this correct?
                            
                            1) Yes.
                            2) No, revise input.
                            """, minYear, maxYear, sortOrder);

                    int confirm = Console.promptForInt("Enter selection: ");

                    switch (confirm) {
                        case 1 -> {
                            displayVehicles(dealership.getVehiclesByMultiYear(minYear, maxYear, lowToHigh));
                            running = false;
                        }
                        case 2 -> running = true;
                        default -> System.out.println("Enter a valid input.");
                    }
                }
            }
        }
    }

    private void processGetByColorRequest() {
        running = true;
        while (running) {
            String color = Console.promptForString("Enter color of vehicle: ").trim();

            //todo add confirmation prompt
            System.out.println("""
                    Select alphabetical sorting order:
                    
                    1) A - Z
                    2) Z - A
                    """);

            boolean lowToHigh = processSetSortingOrder();
            String sortOrder = lowToHigh ? "A - Z" : "Z - A";

            System.out.printf("""
                    You entered:
                    
                    Color: %s
                    Sort : %s
                    
                    Is this correct?
                    
                    1) Yes.
                    2) No, revise input.
                    """, color, sortOrder);

            int confirm = Console.promptForInt("Enter selection: ");
            switch (confirm) {
                case 1 -> {
                    displayVehicles(dealership.getVehiclesByColor(color, lowToHigh));
                    running = false;
                }
                case 2 -> running = true;
                default -> System.out.println("Enter a valid input.");
            }
        }
    }

    private void processGetByMileageRequest() {
        String inputLow = "";
        String inputHigh = "";
        boolean lowToHigh = true;
        int minMileage = 0;
        int maxMileage = 0;
        running = true;
        while (running) {
            //Loop that performs exception handling for LOWEST mileage; prompts again if triggered.
            while (true) {
                try {
                    inputLow = Console.promptForString("Enter minimum mileage, or leave blank for inventory minimum: ").trim();
                    minMileage = inputLow.isEmpty() ? 0 : Integer.parseInt(inputLow);

                    if (minMileage < 0) {
                        throw new IllegalArgumentException("Lowest mileage must be greater than 0 miles.");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            //Loop that performs exception handling for HIGHEST mileage; prompts again if triggered.
            while (true) {
                try {
                    inputHigh = Console.promptForString("Enter maximum mileage, or leave blank for inventory maximum: ").trim();
                    maxMileage = inputHigh.isEmpty() ? 150000 : Integer.parseInt(inputHigh);
                    if (maxMileage > 150000) {
                        throw new IllegalArgumentException("Maximum mileage must be less than 150,000 miles.");
                    }
                    if (maxMileage < minMileage) {
                        throw new IllegalArgumentException("Maximum mileage cannot be less than minimum mileage.");
                    }
                    if (inputLow.isEmpty() && inputHigh.isEmpty()) {
                        throw new IllegalArgumentException("Both fields cannot be empty.");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("""
                    Select sorting order:
                    
                    1) Lowest to highest
                    2) Highest to lowest
                    """);

            lowToHigh = processSetSortingOrder();
            String sortOrder = lowToHigh ? "Lowest to highest" : "Highest to lowest";

            System.out.printf("""
                    You entered:
                    
                    Mileage: %d mi. - %d mi.
                    Sort   : %s
                    
                    Is this correct?
                    
                    1) Yes.
                    2) No, revise input.
                    """, minMileage, maxMileage, sortOrder);
            int confirm = Console.promptForInt("Enter selection: ");
            switch (confirm) {
                case 1 -> {
                    displayVehicles(dealership.getVehiclesByMileage(minMileage, maxMileage, lowToHigh));
                    running = false;
                }
                case 2 -> running = true;
                default -> System.out.println("Enter a valid input.");
            }
        }
    }

    private void processGetByVehicleTypeRequest() {
        boolean lowToHigh = true;
        running = true;
        while (running) {
            try {
                System.out.println("""
                        Enter vehicle types (3 max.)
                        
                        - Car
                        - Van
                        - Truck
                        - SUV""");

                String input = Console.promptForString("Enter vehicle type(s): ");
                String[] inputTypes = input.split("[,\\s]+");

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Minimum vehicle type entry is 1.");
                } else if (inputTypes.length > 3) {
                    throw new IllegalArgumentException("Maximum vehicle type entry is 3.");
                }
                System.out.println("""
                        Select sorting order:
                        
                        1) Lowest to highest
                        2) Highest to lowest
                        """);

                lowToHigh = processSetSortingOrder();
                String sortOrder = lowToHigh ? "Lowest to highest" : "Highest to lowest";

                System.out.println("You entered:\n");
                if (inputTypes.length == 1) {
                    System.out.printf("Vehicle Type: %s\nSort        : %s\n", inputTypes[0], sortOrder);
                } else if (inputTypes.length == 2) {
                    System.out.printf("Vehicle Types: %s, %s\nSort         : %s\n", inputTypes[0], inputTypes[1], sortOrder);
                } else if (inputTypes.length == 3) {
                    System.out.printf("Vehicle Types: %s, %s, %s\nSort         : %s\n", inputTypes[0], inputTypes[1], inputTypes[2], sortOrder);
                }

                System.out.println("""
                        \nIs this correct?
                        
                        1) Yes
                        2) No, revise input.""");

                int confirm = Console.promptForInt("Enter selection: ");
                switch (confirm) {
                    case 1 -> {
                        displayVehicles(dealership.getVehiclesByType(lowToHigh, inputTypes));
                        running = false;
                    }
                    case 2 -> running = true;
                    default -> System.out.println("Enter a valid input.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private void processGetAllVehiclesRequest() throws FileNotFoundException {
        displayVehicles(dealership.getAllVehicles());

    }

    private void processAddVehicleRequest() throws IOException {
        int vin;
        int year;
        String make;
        String model;
        String vehicleType;
        String color;
        int odometer;
        BigDecimal price;
        while (true) {
            try {
                vin = Console.promptForInt("Enter VIN number: ");
                for (Vehicle cars : dealership.inventory) {
                    if (vin == cars.getVin()) {
                        throw new IllegalArgumentException("VIN already exists in database. Enter a new 6-digit VIN number.");
                    }
                }
                if (vin < 100000 || vin > 999999) {
                    throw new IllegalArgumentException("VIN number must be 6 digits long.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Exception handler checking if new vehicle is a duplicate of a vehicle in the list.
        while (true) {
            try {
                while (true) {
                    year = Console.promptForInt("Enter year of vehicle: ");
                    try {
                        if (year < 1990 || year > 2026) {
                            throw new IllegalArgumentException("Invalid year entry. Enter a year between 1990 - 2026.");
                        }
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                    make = Console.promptForString("Enter vehicle make: ").trim();
                    model = Console.promptForString("Enter vehicle model: ").trim();

                    while (true) {
                        vehicleType = Console.promptForString("Enter vehicle type: ").trim();
                        try {
                            if (vehicleType.equalsIgnoreCase("car")) {
                                vehicleType = "Car";
                            } else if (vehicleType.equalsIgnoreCase("van")) {
                                vehicleType = "Van";
                            } else if (vehicleType.equalsIgnoreCase("truck")) {
                                vehicleType = "Truck";
                            } else if (vehicleType.equalsIgnoreCase("suv")) {
                                vehicleType = "SUV";
                            } else {
                                throw new IllegalArgumentException("Invalid vehicle type entry. Enter only Car, Van, Truck, or SUV.");
                            }
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    color = Console.promptForString("Enter vehicle color: ").trim();

                    while (true) {
                        odometer = Console.promptForInt("Enter odometer reading: ");
                        try {
                            if (odometer < 1000) {
                                throw new IllegalArgumentException("Invalid mileage entry. Enter only a mileage above 1,000 miles.");
                            }
                            if (odometer > 200000) {
                                throw new IllegalArgumentException("Invalid mileage entry. Enter only a mileage below 200,000 miles.");
                            }
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    while (true) {
                        price = BigDecimal.valueOf(Console.promptForDouble("Enter vehicle price: "));
                        try {
                            BigDecimal minAllowed = new BigDecimal("5000");

                            if (price.compareTo(minAllowed) < 0) {
                                throw new IllegalArgumentException("Invalid price entry. Enter only a price above $5,000.");
                            }
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    for (Vehicle cars : dealership.inventory) {
                        if (year == cars.getYear()
                                && make.equalsIgnoreCase(cars.getMake())
                                && model.equalsIgnoreCase(cars.getModel())
                                && vehicleType.equalsIgnoreCase(cars.getVehicleType())
                                && color.equalsIgnoreCase(cars.getColor())
                                && odometer == cars.getOdometer()
                                && price.compareTo(cars.getPrice()) == 0) {
                            throw new IllegalArgumentException("Entered vehicle already exists in inventory. Please enter new values.");
                        }
                    }
                Vehicle newCar = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                dealership.addVehicle(newCar);
                DealershipFileManager.saveDealership(dealership);
                System.out.println("Thank you.");
                break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
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

    private boolean processSetSortingOrder() {

        int option = Console.promptForInt("Enter selection: ");
        boolean lowToHigh = true;

        switch (option) {
            case 1 -> lowToHigh = true;
            case 2 -> lowToHigh = false;
            case 99 -> {
            }
            default -> System.out.println("Invalid option. Enter a valid input.");
        }
        return lowToHigh;
    }
}
