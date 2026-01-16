/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dell
 */
import model.Sale;

public class SalesStack {
    private Sale[] arr;
    private int top;
    private int capacity;

    // Constructor to initialize stack
    public SalesStack(int size) {
        arr = new Sale[size];
        capacity = size;
        top = -1; // Indicates empty stack 
    }

    // Push Operation: Add sale to top
    public void push(Sale x) {
        if (isFull()) {
            System.out.println("Stack OverFlow"); 
            // In GUI, you might want to show a popup message here
            return;
        }
        arr[++top] = x; // Increment top then add element
    }

    // Pop Operation: Remove sale from top
    public Sale pop() {
        if (isEmpty()) {
            System.out.println("STACK EMPTY"); 
            return null;
        }
        return arr[top--]; // Return element then decrement top 
    }

    // Check if empty
    public boolean isEmpty() {
        return top == -1; // [cite: 773]
    }

    // Check if full
    public boolean isFull() {
        return top == capacity - 1; 
    }
    
    // Peek: Look at the top without removing
    public Sale peek() {
        if (!isEmpty()) {
            return arr[top];
        }
        return null;
    }
    public model.Sale[] getItems() {
    // Return a copy or the actual array up to 'top'
    model.Sale[] currentItems = new model.Sale[top + 1];
    for (int i = 0; i <= top; i++) {
        currentItems[i] = arr[i];
    }
    return currentItems;
}
}
