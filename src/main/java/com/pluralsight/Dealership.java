package com.pluralsight;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    public String getName() {
        
        return null;
    }

    public void setName(String name) {
        
    }

    public String getAddress() {
        
        return null;
    }

    public void setAddress(String address) {
        
    }

    public String getPhone() {
        
        return null;
    }

    public void setPhone(String phone) {
        
    }

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public List<Vehicle> getVehiclesByPrice(BigDecimal min, BigDecimal max) {
        
        return null;
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