package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class generate_bill {

    private Scanner scanner;
    private final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    public generate_bill() {
        this.scanner = new Scanner(System.in);
    }

    private String selectMonth() {
        System.out.println("\nSelect Month:");
        for (int i = 0; i < MONTHS.length; i++) {
            System.out.println((i + 1) + ". " + MONTHS[i]);
        }
        System.out.print("Enter month number (1-12): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= 12) return MONTHS[choice - 1];
        } catch (NumberFormatException e) {}
        return null;
    }

    public void generateBill() {
        ConsoleHelper.printHeader("GENERATE ELECTRICITY BILL");

        System.out.print("Enter Meter Number: ");
        String meter = scanner.nextLine().trim();

        String month = selectMonth();
        if (month == null) {
            ConsoleHelper.printError("Invalid month selection.");
            return;
        }

        try {
            conn c = new conn();

            // Header
            System.out.println("\n");
            System.out.println("        *** Smart Power Utility ***");
            System.out.println("  ELECTRICITY BILL FOR THE MONTH OF " + month + " 2025");
            ConsoleHelper.printDivider();

            // Customer Details
            PreparedStatement ps1 = c.c.prepareStatement(
                "SELECT * FROM emp WHERE meter_number = ?"
            );
            ps1.setString(1, meter);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                System.out.println("Customer Name  : " + rs.getString("name"));
                System.out.println("Meter Number   : " + rs.getString("meter_number"));
                System.out.println("Address        : " + rs.getString("address"));
                System.out.println("State          : " + rs.getString("state"));
                System.out.println("City           : " + rs.getString("city"));
                System.out.println("Email          : " + rs.getString("email"));
                System.out.println("Phone          : " + rs.getString("phone"));
            } else {
                ConsoleHelper.printError("No customer found with meter number: " + meter);
                return;
            }

            ConsoleHelper.printDivider();

            // Tax Details
            ResultSet rs2 = c.s.executeQuery("SELECT * FROM tax");
            if (rs2.next()) {
                System.out.println("Meter Location : " + rs2.getString("meter_location"));
                System.out.println("Meter Type     : " + rs2.getString("meter_type"));
                System.out.println("Phase Code     : " + rs2.getString("phase_code"));
                System.out.println("Bill Type      : " + rs2.getString("bill_type"));
                System.out.println("Days           : " + rs2.getString("days"));
            }

            ConsoleHelper.printDivider();

            // Bill Details
            PreparedStatement ps2 = c.c.prepareStatement(
                "SELECT * FROM bill WHERE meter_number = ? AND month = ?"
            );
            ps2.setString(1, meter);
            ps2.setString(2, month);
            ResultSet rs3 = ps2.executeQuery();

            if (rs3.next()) {
                System.out.println("Bill Date      : " + rs3.getDate("bill_date"));
                System.out.println("Units Consumed : " + rs3.getString("units"));
                System.out.println("Total Charges  : Rs. " + rs3.getString("amount"));
                ConsoleHelper.printDivider();
                System.out.println("TOTAL PAYABLE  : Rs. " + rs3.getString("amount"));
                ConsoleHelper.printDivider();
            } else {
                System.out.println("\n[WARNING] Bill not calculated for this month.");
                System.out.println("Please calculate the bill first using Calculate Bill option.");
            }

        } catch (Exception e) {
            ConsoleHelper.printError("Error generating bill: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new generate_bill().generateBill();
    }
}
