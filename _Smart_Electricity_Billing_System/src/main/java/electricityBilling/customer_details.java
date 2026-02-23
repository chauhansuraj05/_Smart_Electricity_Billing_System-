package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class customer_details {

    private Scanner scanner;

    public customer_details() {
        this.scanner = new Scanner(System.in);
    }

    public void viewAllCustomers() {
        ConsoleHelper.printHeader("CUSTOMER DETAILS");

        try {
            conn c1 = new conn();
            ResultSet rs = c1.s.executeQuery("SELECT * FROM emp");

            System.out.printf("%-20s %-15s %-25s %-15s %-15s %-25s %-15s%n",
                "Name", "Meter No", "Address", "State", "City", "Email", "Phone");
            ConsoleHelper.printDivider();
            ConsoleHelper.printDivider();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-20s %-15s %-25s %-15s %-15s %-25s %-15s%n",
                    rs.getString("name"),
                    rs.getString("meter_number"),
                    rs.getString("address"),
                    rs.getString("state"),
                    rs.getString("city"),
                    rs.getString("email"),
                    rs.getString("phone")
                );
            }

            if (!found) {
                ConsoleHelper.printInfo("No customers found.");
            }

        } catch (Exception e) {
            ConsoleHelper.printError("Failed to fetch customer details: " + e.getMessage());
        }
    }

    public void searchCustomer() {
        ConsoleHelper.printHeader("SEARCH CUSTOMER");
        System.out.print("Enter Meter Number to search: ");
        String meter = scanner.nextLine().trim();

        try {
            conn c1 = new conn();
            PreparedStatement ps = c1.c.prepareStatement(
                "SELECT * FROM emp WHERE meter_number = ?"
            );
            ps.setString(1, meter);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ConsoleHelper.printDivider();
                System.out.println("Name         : " + rs.getString("name"));
                System.out.println("Meter Number : " + rs.getString("meter_number"));
                System.out.println("Address      : " + rs.getString("address"));
                System.out.println("State        : " + rs.getString("state"));
                System.out.println("City         : " + rs.getString("city"));
                System.out.println("Email        : " + rs.getString("email"));
                System.out.println("Phone        : " + rs.getString("phone"));
                ConsoleHelper.printDivider();
            } else {
                ConsoleHelper.printError("No customer found with meter number: " + meter);
            }

        } catch (Exception e) {
            ConsoleHelper.printError("Search failed: " + e.getMessage());
        }
    }

    public void run() {
        System.out.println("\n1. View All Customers");
        System.out.println("2. Search Customer by Meter No");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1": viewAllCustomers(); break;
            case "2": searchCustomer(); break;
            default: ConsoleHelper.printError("Invalid option.");
        }
    }

    public static void main(String[] args) {
        new customer_details().run();
    }
}
