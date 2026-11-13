package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private final ArrayList<Vehicle> inventory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public List<Vehicle> getVehiclesByPrice(BigDecimal min, BigDecimal max) {
        List<Vehicle> withinPrice = new ArrayList<>();

        for (Vehicle cars : inventory) {
            BigDecimal price = cars.getPrice();

            if (price.compareTo(min) >= 0 && price.compareTo(max) <=0) {
                withinPrice.add(cars);
            }
        }
        return withinPrice;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        
        return null;
    }

    public List<Vehicle> getVehiclesByYear(int min, int max) {
        
        return null;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        
        return null;
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        
        return null;
    }

    public List<Vehicle> getVehiclesByType(String vehicleType) {
        
        return null;
    }

    public List<Vehicle> getAllVehicles() {
        return inventory;
    }

    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        
    }
}