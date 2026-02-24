# ☕ Java Vending Machine System

A console-based Vending Machine application built using Core Java and JDBC. This system simulates a real-world vending machine with separate interfaces for customers to purchase items and administrators to manage inventory.

## 🚀 Tech Stack
* **Language:** Java
* **Database:** MySQL
* **Libraries:** `java.sql.*`, `java.util.Scanner`

## ✨ Features
* **Customer Interface:** Users can view products, select items, and make purchases.
* **Admin Interface:** Administrators can log in to verify and manage the vending machine's inventory and data.
* **Database Integration:** Utilizes JDBC to securely store and retrieve inventory and transaction data from a MySQL database (`vendingmachinedatabase`).

## 📸 Project Screenshots

### Customer Menu
<img width="442" height="369" alt="Customer" src="https://github.com/user-attachments/assets/cf4eebbc-5093-42f5-adf1-4ef12982d08d" />


### Admin Dashboard
<img width="433" height="380" alt="Admin1" src="https://github.com/user-attachments/assets/a9377dcb-c8f3-4737-bc0f-619fad21afb4" />


## 🛠️ How to Run
1. Clone the repository to your local machine.
2. Ensure you have a local MySQL server running.
3. Create a database named `vendingmachinedatabase`.
4. Update the JDBC connection string in `VendingMachineProject.java` if your port or credentials differ.
5. Compile and run the Java file.
