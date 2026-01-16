package controller;

import model.Car;
import java.util.ArrayList;
import model.Sale;

public class CarController {

    // 1. INVENTORY (ArrayList - Existing Requirement)
    private static ArrayList<Car> inventory = new ArrayList<>();

    // 2. SALES DATA STRUCTURES 
    // defined as static 
    private static SalesQueue pendingSales = new SalesQueue(50); // Capacity of 50 pending sales
    private static SalesStack salesHistory = new SalesStack(100); // Capacity of 100 history items

    public CarController() {
        // Dummy data for testing
        if (inventory.isEmpty()) {
            inventory.add(new Car(101, "BMW", "X5", 2023, 65000, 5));
            inventory.add(new Car(102, "BMW", "M3", 2022, 72000, 3));
            inventory.add(new Car(103, "BMW", "i4", 2024, 55000, 10));
            inventory.add(new Car(104, "BMW", "X7", 2023, 90000, 2));
            inventory.add(new Car(105, "BMW", "Z4", 2021, 50000, 4));
        }
    }

    public ArrayList<Car> getAllCars() {
        return inventory;
    }

    public Car getCarById(int id) {
        for (Car car : inventory) {
            if (car.getCarId() == id) {
                return car;
            }
        }
        return null;
    }

    public boolean registerCar(Car newCar) {
        for (Car car : inventory) {
            if (car.getCarId() == newCar.getCarId()) {
                return false; // Duplicate ID
            }
        }
        inventory.add(newCar);
        return true;
    }

    public boolean updateCar(int id, String newModel, double newPrice, int newQuantity) {
        Car car = getCarById(id);
        if (car != null) {
            car.setModel(newModel);
            car.setPrice(newPrice);
            car.setStockQuantity(newQuantity);
            return true;
        }
        return false;
    }

    public boolean deleteCar(int id) {
        Car car = getCarById(id);
        if (car != null) {
            inventory.remove(car);
            return true;
        }
        return false;
    }

    /**
     * Adds a sale to the PENDING QUEUE (FIFO). This uses your manual
     * Array-based Queue.
     *
     * @param sale The sale object containing items and customer info.
     */
    public void addSaleToQueue(Sale sale) {
        if (!pendingSales.isFull()) {
            pendingSales.enQueue(sale); // Uses logic from Slide 44 [cite: 1454-1455]
            System.out.println("Sale added to pending queue: " + sale.getCustomerName());
        } else {
            System.out.println("Queue is full! Cannot add more sales.");
        }
    }

    // Helper to get the queue for display purposes (if you want to show pending list)
    public SalesQueue getPendingQueue() {
        return pendingSales;
    }

    // Helper to get stack for display purposes
    public SalesStack getSalesHistory() {
        return salesHistory;
    }

    public model.Sale cancelNextSale() {
        if (!pendingSales.isEmpty()) {
            // Standard Dequeue operation - Slide 45 [cite: 1481-1483]
            model.Sale cancelledSale = pendingSales.deQueue();

            System.out.println("Sale cancelled and removed from queue: " + cancelledSale.getCustomerName());
            return cancelledSale;
        }
        return null; // Queue was empty
    }

    public model.Sale confirmNextSale() {
        if (!pendingSales.isEmpty()) {
            // 1. Dequeue the sale (FIFO)
            model.Sale sale = pendingSales.deQueue();

            // 2. UPDATE STOCK (Decrease Quantity)
            // Loop through every item that was in the cart for this sale
            for (model.Car soldCar : sale.getPurchasedItems()) {
                // Find the "real" car object in your main inventory using its ID
                model.Car inventoryCar = getCarById(soldCar.getCarId());

                if (inventoryCar != null) {
                    int currentQty = inventoryCar.getStockQuantity();
                    // Update the stock (Current Stock - 1)
                    inventoryCar.setStockQuantity(currentQty - 1);
                }
            }

            // 3. Push to History (LIFO)
            salesHistory.push(sale);

            System.out.println("Sale Confirmed. Inventory Updated.");
            return sale;
        }
        return null; // Nothing to confirm
    }

    public model.Sale undoLastSale() {
        if (!salesHistory.isEmpty()) {

            // 1. Pop from History (LIFO) - Slide 21 [cite: 741-744]
            model.Sale undoneSale = salesHistory.pop();

            // 2. RESTORE STOCK (Increase Quantity)
            for (model.Car soldCar : undoneSale.getPurchasedItems()) {
                model.Car inventoryCar = getCarById(soldCar.getCarId());

                if (inventoryCar != null) {
                    int currentQty = inventoryCar.getStockQuantity();
                    // Add the stock back!
                    inventoryCar.setStockQuantity(currentQty + 1);
                }
            }

            System.out.println("Sale Undone. Stock Restored.");
            return undoneSale;
        }
        return null; // Stack was empty
    }
    // ==========================================
    //       PART D: SEARCH & SORT ALGORITHMS
    // ==========================================


    public java.util.ArrayList<Car> searchByModel(String query) {
        java.util.ArrayList<Car> results = new java.util.ArrayList<>();

        // Iterate through the inventory
        for (Car car : inventory) {
            // Check if model contains the query (case-insensitive)
            if (car.getModel().toLowerCase().contains(query.toLowerCase())) {
                results.add(car);
            }
        }
        return results;
    }
    // ==========================================
    //       PART D: ALGORITHMS (Bubble Sort & Binary Search)
    // ==========================================

    public void bubbleSort(String criteria, boolean isAscending) {
        int n = inventory.size();

        // Outer loop for number of passes
        for (int i = 0; i < n - 1; i++) {
            // Inner loop for comparison
            for (int j = 0; j < n - i - 1; j++) {

                Car c1 = inventory.get(j);
                Car c2 = inventory.get(j + 1);
                boolean condition = false;

                // 1. Sort by PRICE (Used by your Sort Button)
                if (criteria.equalsIgnoreCase("price")) {
                    if (isAscending) {
                        condition = c1.getPrice() > c2.getPrice(); // Low to High
                    } else {
                        condition = c1.getPrice() < c2.getPrice(); // High to Low
                    }
                } // 2. Sort by BRAND (Required before Binary Search on Brand)
                else if (criteria.equalsIgnoreCase("brand")) {
                    int compare = c1.getBrand().compareToIgnoreCase(c2.getBrand());
                    condition = isAscending ? (compare > 0) : (compare < 0);
                } // 3. Sort by MODEL (Required before Binary Search on Model)
                else if (criteria.equalsIgnoreCase("model")) {
                    int compare = c1.getModel().compareToIgnoreCase(c2.getModel());
                    condition = isAscending ? (compare > 0) : (compare < 0);
                }

                // SWAP Logic (if condition is met)
                if (condition) {
                    inventory.set(j, c2);
                    inventory.set(j + 1, c1);
                }
            }
        }
    }


    public java.util.ArrayList<Car> binarySearch(String searchValue, String criteria) {
        java.util.ArrayList<Car> foundItems = new java.util.ArrayList<>();

        int low = 0;
        int high = inventory.size() - 1;

        // Divide and Conquer strategy
        while (low <= high) {
            int mid = low + (high - low) / 2;
            Car midCar = inventory.get(mid);

            // Fetch the value we are comparing (Brand or Model)
            String midValue = criteria.equalsIgnoreCase("brand") ? midCar.getBrand() : midCar.getModel();

            int compare = midValue.compareToIgnoreCase(searchValue);

            if (compare == 0) {
                // FOUND! match at 'mid'
                // Binary Search finds *one* match. We need to check neighbors for duplicates.
                foundItems.add(midCar);

                // Check Left Neighbors
                int left = mid - 1;
                while (left >= 0) {
                    Car c = inventory.get(left);
                    String val = criteria.equalsIgnoreCase("brand") ? c.getBrand() : c.getModel();
                    if (val.equalsIgnoreCase(searchValue)) {
                        foundItems.add(c);
                        left--;
                    } else {
                        break;
                    }
                }

                // Check Right Neighbors
                int right = mid + 1;
                while (right < inventory.size()) {
                    Car c = inventory.get(right);
                    String val = criteria.equalsIgnoreCase("brand") ? c.getBrand() : c.getModel();
                    if (val.equalsIgnoreCase(searchValue)) {
                        foundItems.add(c);
                        right++;
                    } else {
                        break;
                    }
                }
                return foundItems; // Return list immediately after finding block
            }

            if (compare < 0) {
                low = mid + 1; // Search Right Half
            } else {
                high = mid - 1; // Search Left Half
            }
        }
        return foundItems; // Return empty list if not found
    }

    public java.util.ArrayList<Car> searchByBrandAndModel(String query) {
        java.util.ArrayList<Car> finalResults = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> addedIds = new java.util.ArrayList<>(); // To track duplicates

        // --- STEP 1: Search in BRAND ---
        // Binary Search REQUIREMENT: Sort by Brand first
        bubbleSort("brand", true);
        java.util.ArrayList<Car> brandResults = binarySearch(query, "brand");

        for (Car c : brandResults) {
            finalResults.add(c);
            addedIds.add(c.getCarId());
        }

        // --- STEP 2: Search in MODEL ---
        // Binary Search REQUIREMENT: Sort by Model first
        bubbleSort("model", true);
        java.util.ArrayList<Car> modelResults = binarySearch(query, "model");

        for (Car c : modelResults) {
            // Only add if not already added (avoid duplicates)
            if (!addedIds.contains(c.getCarId())) {
                finalResults.add(c);
            }
        }

        // --- STEP 3: Restore Default Sort (Optional, e.g., by Price or ID) ---
        // bubbleSort("price", true); 
        return finalResults;
    }
}
