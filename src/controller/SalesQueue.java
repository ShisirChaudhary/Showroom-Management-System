/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Tejas Shahi
 */
import model.Sale;

public class SalesQueue {

    private int SIZE = 5; // Or whatever capacity you need
    private Sale[] items;
    private int front, rear;

    public SalesQueue(int size) {
        SIZE = size;
        items = new Sale[SIZE];
        front = -1;
        rear = -1; // [cite: 1397]
    }

    // Enqueue: Add element to rear
    public void enQueue(Sale element) {
        if (isFull()) {
            System.out.println("Queue is full");
        } else {
            if (front == -1) {
                front = 0; // Set front to 0 if adding first element [cite: 1454]
            }
            rear++;
            items[rear] = element; // Add element at rear [cite: 1334]
            System.out.println("Inserted " + element.getCustomerName());
        }
    }

    // Dequeue: Remove element from front
    public Sale deQueue() {
        Sale element;
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        } else {
            element = items[front]; // Access data at front [cite: 1484]
            if (front >= rear) {
                // Queue has only one element, so we reset queue after deleting it [cite: 1484]
                front = -1;
                rear = -1;
            } else {
                front++; // Increase front pointer [cite: 1367]
            }
            return element;
        }
    }

    public boolean isFull() {
        return front == 0 && rear == SIZE - 1; // [cite: 1426]
    }

    public boolean isEmpty() {
        return front == -1; // [cite: 1426]
    }

    // Helper to see the next item to be processed
    public Sale peek() {
        if (isEmpty()) {
            return null;
        }
        return items[front];
    }

    public model.Sale[] getItems() {
        if (isEmpty()) {
            return new model.Sale[0];
        }

        // We need to loop from front to rear
        int count = 0;
        // Calculate size based on front/rear logic (simple version for your coursework)
        // If front <= rear
        int size = rear - front + 1;
        model.Sale[] currentItems = new model.Sale[size];

        for (int i = 0; i < size; i++) {
            currentItems[i] = items[front + i];
        }
        return currentItems;
    }
}
