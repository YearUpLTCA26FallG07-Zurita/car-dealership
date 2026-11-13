package com.pluralsight;

import java.math.BigDecimal;

public class Vehicle {
    private int vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private BigDecimal price;

    public Vehicle(int vin, int year, String make, String model,
                   String vehicleType, String color, int odometer, BigDecimal price) {

        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    public int getVin() {return vin; }
    public void setVin(int vin) {   }

    public int getYear() {return year; }
    public void setYear(int year) {   }

    public String getMake() {return make; }
    public void setMake(String make) {   }

    public String getModel() {return model; }
    public void setModel(String model) {   }

    public String getVehicleType() {return vehicleType; }
    public void setVehicleType(String vehicleType) {   }

    public String getColor() {return color; }
    public void setColor(String color) {   }

    public int getOdometer() {return odometer; }
    public void setOdometer(int odometer) {   }

    public BigDecimal getPrice() {return price; }
    public void setPrice(BigDecimal price) {   }

    @Override
    public String toString() {
        return year + " " + make + " " + model + " (" + color + ", " + vehicleType + ")"
                + " VIN: " + vin + " | " + odometer + " mi | $" + price;
    }
}