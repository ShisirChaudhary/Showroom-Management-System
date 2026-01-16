/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;
/**
 *
 * @author Dell
 */
public class Sale {
    private String customerName;
    private String contactNo;
    private ArrayList<Car> purchasedItems; // The "Shopping Cart"
    private double totalAmount;

    public Sale(String customerName, String contactNo, ArrayList<Car> purchasedItems) {
        this.customerName = customerName;
        this.contactNo = contactNo;
        this.purchasedItems = new ArrayList<>(purchasedItems); 
        this.totalAmount = calculateTotal();
    }

    private double calculateTotal() {
        double total = 0;
        for (Car car : purchasedItems) {
            total += car.getPrice(); // In a real system, you'd multiply by qty
        }
        return total;
    }
    
    // Getters for Table Display
    public String getCustomerName() { return customerName; }
    public double getTotalAmount() { return totalAmount; }
    public ArrayList<Car> getPurchasedItems() { return purchasedItems; }
}