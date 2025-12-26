package controller;

import model.Car;
import java.util.ArrayList;

public class CarController {

    // 1. MUST BE STATIC so data is shared across different button clicks
    private static ArrayList<Car> inventory = new ArrayList<>();

    public CarController() {
        // Only add dummy data if the list is completely empty
        if (inventory.isEmpty()) {
            inventory.add(new Car(101, "BMW", "X5", 2023, 65000, 5));
            inventory.add(new Car(102, "BMW", "M3", 2022, 72000, 3));
            inventory.add(new Car(103, "BMW", "i4", 2024, 55000, 10));
            inventory.add(new Car(104, "BMW", "X7", 2023, 90000, 2));
            inventory.add(new Car(105, "BMW", "Z4", 2021, 50000, 4));
        }
    }

    // CREATE: Add a new car
    // READ: Get all cars (Fixed return type to match ArrayList)
    public ArrayList<Car> getAllCars() {
        return inventory;
    }

    // READ: Find a single car by ID
    public Car getCarById(int id) {
        for (Car car : inventory) {
            if (car.getCarId() == id) {
                return car;
            }
        }
        return null; // Not found
    }

    // CREATE: Add a new car (Returns false if ID already exists)
    public boolean registerCar(Car newCar) {
        // 1. Check if ID exists
        for (Car car : inventory) {
            if (car.getCarId() == newCar.getCarId()) {
                return false; // Duplicate found, do not add
            }
        }

        // 2. No duplicate found, add the car
        inventory.add(newCar);
        System.out.println("Car added successfully: " + newCar.getBrand());
        return true;
    }

    // UPDATE: Updates Model, Price, and Quantity (Used by your Update Tab)
    public boolean updateCar(int id, String newModel, double newPrice, int newQuantity) {
        Car car = getCarById(id); // Re-using your helper method

        if (car != null) {
            car.setModel(newModel);
            car.setPrice(newPrice);
            car.setStockQuantity(newQuantity);
            return true; // Success
        }
        return false; // ID not found
    }
// DELETE: Remove a car by ID and return true if successful

    public boolean deleteCar(int id) {
        Car car = getCarById(id);
        if (car != null) {
            inventory.remove(car);
            return true; // Deleted successfully
        }
        return false; // ID not found
    }
}
