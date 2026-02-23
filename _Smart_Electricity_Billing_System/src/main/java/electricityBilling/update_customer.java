package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class update_customer {

    private Scanner scanner;

    public update_customer() {
        this.scanner = new Scanner(System.in);
    }

    public void updateCustomer() {
        ConsoleHelper.printHeader("UPDATE CUSTOMER DETAILS");

        System.out.print("Enter Meter Number of customer to update: ");
        String meter = scanner.nextLine().trim();

        try {
            conn c = new conn();
            PreparedStatement ps1 = c.c.prepareStatement(
                "SELECT * FROM emp WHERE meter_number = ?"
            );
            ps1.setString(1, meter);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                ConsoleHelper.printError("No customer found with meter number: " + meter);
                return;
            }

            // Show current details
            System.out.println("\nCurrent Details (press Enter to keep existing value):");
            ConsoleHelper.printDivider();

            String currentName    = rs.getString("name");
            String currentAddress = rs.getString("address");
            String currentState   = rs.getString("state");
            String currentCity    = rs.getString("city");
            String currentEmail   = rs.getString("email");
            String currentPhone   = rs.getString("phone");

            System.out.print("Name [" + currentName + "]: ");
            String newName = scanner.nextLine().trim();
            if (newName.isEmpty()) newName = currentName;

            System.out.print("Address [" + currentAddress + "]: ");
            String newAddress = scanner.nextLine().trim();
            if (newAddress.isEmpty()) newAddress = currentAddress;

            System.out.print("State [" + currentState + "]: ");
            String newState = scanner.nextLine().trim();
            if (newState.isEmpty()) newState = currentState;

            System.out.print("City [" + currentCity + "]: ");
            String newCity = scanner.nextLine().trim();
            if (newCity.isEmpty()) newCity = currentCity;

            System.out.print("Email [" + currentEmail + "]: ");
            String newEmail = scanner.nextLine().trim();
            if (newEmail.isEmpty()) newEmail = currentEmail;

            System.out.print("Phone [" + currentPhone + "]: ");
            String newPhone = scanner.nextLine().trim();
            if (newPhone.isEmpty()) newPhone = currentPhone;

            // Update DB
            PreparedStatement ps2 = c.c.prepareStatement(
                "UPDATE emp SET name=?, address=?, state=?, city=?, email=?, phone=? WHERE meter_number=?"
            );
            ps2.setString(1, newName);
            ps2.setString(2, newAddress);
            ps2.setString(3, newState);
            ps2.setString(4, newCity);
            ps2.setString(5, newEmail);
            ps2.setString(6, newPhone);
            ps2.setString(7, meter);
            ps2.executeUpdate();

            ConsoleHelper.printSuccess("Customer details updated successfully!");

        } catch (Exception e) {
            ConsoleHelper.printError("Update failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new update_customer().updateCustomer();
    }
}
