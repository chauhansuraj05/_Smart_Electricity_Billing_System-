package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class new_customer {

    private Scanner scanner;

    public new_customer() {
        this.scanner = new Scanner(System.in);
    }

    public void addCustomer() {
        ConsoleHelper.printHeader("ADD NEW CUSTOMER");

        System.out.print("Enter Name           : ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Meter Number   : ");
        String meterNo = scanner.nextLine().trim();

        System.out.print("Enter Address        : ");
        String address = scanner.nextLine().trim();

        System.out.print("Enter State          : ");
        String state = scanner.nextLine().trim();

        System.out.print("Enter City           : ");
        String city = scanner.nextLine().trim();

        System.out.print("Enter Email          : ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Phone Number   : ");
        String phone = scanner.nextLine().trim();

        // Basic validation
        if (name.isEmpty() || meterNo.isEmpty() || phone.isEmpty()) {
            ConsoleHelper.printError("Name, Meter Number, and Phone are required fields.");
            return;
        }

        try {
            conn c1 = new conn();
            String q1 = "INSERT INTO emp VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = c1.c.prepareStatement(q1);
            ps.setString(1, name);
            ps.setString(2, meterNo);
            ps.setString(3, address);
            ps.setString(4, state);
            ps.setString(5, city);
            ps.setString(6, email);
            ps.setString(7, phone);
            ps.executeUpdate();
            ConsoleHelper.printSuccess("Customer '" + name + "' added successfully!");

        } catch (Exception e) {
            ConsoleHelper.printError("Failed to add customer: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new new_customer().addCustomer();
    }
}
