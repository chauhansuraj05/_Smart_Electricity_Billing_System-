package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class pay_bill {

    private Scanner scanner;

    public pay_bill() {
        this.scanner = new Scanner(System.in);
    }

    private void listMeterNumbers() {
        try {
            conn c = new conn();
            ResultSet rs = c.s.executeQuery("SELECT meter_number, name FROM emp");
            System.out.println("\nAvailable Meter Numbers:");
            ConsoleHelper.printDivider();
            System.out.printf("%-20s %-20s%n", "Meter Number", "Customer Name");
            ConsoleHelper.printDivider();
            while (rs.next()) {
                System.out.printf("%-20s %-20s%n",
                    rs.getString("meter_number"),
                    rs.getString("name")
                );
            }
            ConsoleHelper.printDivider();
        } catch (Exception e) {
            ConsoleHelper.printError("Could not load meter numbers: " + e.getMessage());
        }
    }

    private void showPendingBills(String meter) {
        try {
            conn c = new conn();
            PreparedStatement ps = c.c.prepareStatement(
                "SELECT month, amount, payment_status FROM bill WHERE meter_number = ?"
            );
            ps.setString(1, meter);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nBill Summary for Meter: " + meter);
            ConsoleHelper.printDivider();
            System.out.printf("%-15s %-15s %-15s%n", "Month", "Amount (Rs.)", "Status");
            ConsoleHelper.printDivider();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-15s %-15s %-15s%n",
                    rs.getString("month"),
                    rs.getString("amount"),
                    rs.getString("payment_status") == null ? "PENDING" : rs.getString("payment_status")
                );
            }
            if (!found) {
                ConsoleHelper.printInfo("No bills found for this meter.");
            }
            ConsoleHelper.printDivider();
        } catch (Exception e) {
            ConsoleHelper.printError("Error fetching bills: " + e.getMessage());
        }
    }

    public void payBill() {
        ConsoleHelper.printHeader("PAY ELECTRICITY BILL");

        listMeterNumbers();

        System.out.print("Enter Meter Number: ");
        String meter = scanner.nextLine().trim();

        showPendingBills(meter);

        System.out.println("\nPayment Options:");
        System.out.println("1. Pay via Online (Paytm - Open link in browser)");
        System.out.println("2. Mark Bill as PAID");
        System.out.println("3. Cancel");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.println("\n[INFO] Open the following link in your browser to pay:");
                System.out.println("       https://paytm.com/electricity-bill-payment");
                System.out.println("[INFO] After payment, come back and use option 2 to mark as PAID.");
                break;

            case "2":
                try {
                    conn c = new conn();
                    PreparedStatement ps = c.c.prepareStatement(
                        "UPDATE bill SET payment_status='PAID' WHERE meter_number=?"
                    );
                    ps.setString(1, meter);
                    int updated = ps.executeUpdate();

                    if (updated > 0) {
                        ConsoleHelper.printSuccess("Payment Successful! Bill marked as PAID for meter: " + meter);
                    } else {
                        ConsoleHelper.printError("No bill found for this meter.");
                    }
                } catch (Exception e) {
                    ConsoleHelper.printError("Payment update failed: " + e.getMessage());
                }
                break;

            case "3":
                ConsoleHelper.printInfo("Payment cancelled.");
                break;

            default:
                ConsoleHelper.printError("Invalid option.");
        }
    }

    public static void main(String[] args) {
        new pay_bill().payBill();
    }
}
