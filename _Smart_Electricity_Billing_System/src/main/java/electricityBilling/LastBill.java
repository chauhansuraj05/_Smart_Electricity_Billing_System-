package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class LastBill {

    private Scanner scanner;

    public LastBill() {
        this.scanner = new Scanner(System.in);
    }

    public void viewLastBill() {
        ConsoleHelper.printHeader("LAST BILL DETAILS");

        System.out.print("Enter Meter Number: ");
        String meter = scanner.nextLine().trim();

        try {
            conn c = new conn();

            // Customer Details
            PreparedStatement ps1 = c.c.prepareStatement(
                "SELECT * FROM emp WHERE meter_number = ?"
            );
            ps1.setString(1, meter);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                ConsoleHelper.printDivider();
                System.out.println("Customer Name  : " + rs.getString("name"));
                System.out.println("Meter Number   : " + rs.getString("meter_number"));
                System.out.println("Address        : " + rs.getString("address"));
                System.out.println("State          : " + rs.getString("state"));
                System.out.println("City           : " + rs.getString("city"));
                System.out.println("Email          : " + rs.getString("email"));
                System.out.println("Phone          : " + rs.getString("phone"));
                ConsoleHelper.printDivider();
            } else {
                ConsoleHelper.printError("No customer found with meter number: " + meter);
                return;
            }

            // Bill History
            System.out.println("\n--- Bill History ---");
            System.out.printf("%-15s %-15s %-15s%n", "Month", "Amount (Rs.)", "Bill Date");
            ConsoleHelper.printDivider();

            PreparedStatement ps2 = c.c.prepareStatement(
                "SELECT month, amount, bill_date FROM bill WHERE meter_number = ? ORDER BY bill_date DESC"
            );
            ps2.setString(1, meter);
            ResultSet rs2 = ps2.executeQuery();

            boolean found = false;
            while (rs2.next()) {
                found = true;
                System.out.printf("%-15s %-15s %-15s%n",
                    rs2.getString("month"),
                    "Rs. " + rs2.getString("amount"),
                    rs2.getDate("bill_date")
                );
            }

            if (!found) {
                ConsoleHelper.printInfo("No bill history available for this meter.");
            }

            ConsoleHelper.printDivider();

        } catch (Exception e) {
            ConsoleHelper.printError("Error fetching bill history: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new LastBill().viewLastBill();
    }
}
