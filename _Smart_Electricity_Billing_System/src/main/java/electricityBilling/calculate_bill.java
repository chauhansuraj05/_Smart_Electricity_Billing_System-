package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class calculate_bill {

    private Scanner scanner;
    private final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    public calculate_bill() {
        this.scanner = new Scanner(System.in);
    }

    private void listMeterNumbers() {
        try {
            conn c = new conn();
            ResultSet rs = c.s.executeQuery("SELECT meter_number FROM emp");
            System.out.println("\nAvailable Meter Numbers:");
            ConsoleHelper.printDivider();
            int idx = 1;
            while (rs.next()) {
                System.out.println(idx++ + ". " + rs.getString("meter_number"));
            }
            ConsoleHelper.printDivider();
        } catch (Exception e) {
            ConsoleHelper.printError("Could not load meter numbers: " + e.getMessage());
        }
    }

    private String selectMonth() {
        System.out.println("\nSelect Month:");
        ConsoleHelper.printDivider();
        for (int i = 0; i < MONTHS.length; i++) {
            System.out.println((i + 1) + ". " + MONTHS[i]);
        }
        System.out.print("Enter month number (1-12): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= 12) {
                return MONTHS[choice - 1];
            }
        } catch (NumberFormatException e) {}
        ConsoleHelper.printError("Invalid month selection.");
        return null;
    }

    public void calculateAndStoreBill() {
        ConsoleHelper.printHeader("CALCULATE ELECTRICITY BILL");

        listMeterNumbers();
        System.out.print("Enter Meter Number: ");
        String meter = scanner.nextLine().trim();

        String month = selectMonth();
        if (month == null) return;

        System.out.print("Enter Units Consumed: ");
        String unitsInput = scanner.nextLine().trim();

        if (unitsInput.isEmpty()) {
            ConsoleHelper.printError("Units cannot be empty.");
            return;
        }

        try {
            int units = Integer.parseInt(unitsInput);

            // Bill Calculation Logic
            int energyCharge = units * 7;
            int fixedCharge  = 50;
            int meterRent    = 12;
            int serviceTax   = 102;
            int otherCharges = 20;
            int surcharge    = 50;
            int totalAmount  = energyCharge + fixedCharge + meterRent + serviceTax + otherCharges + surcharge;

            // Display Bill Breakdown
            ConsoleHelper.printDivider();
            System.out.println("Bill Breakdown for Meter: " + meter);
            ConsoleHelper.printDivider();
            System.out.printf("%-25s : Rs. %d%n", "Energy Charge (Units x 7)", energyCharge);
            System.out.printf("%-25s : Rs. %d%n", "Fixed Charge", fixedCharge);
            System.out.printf("%-25s : Rs. %d%n", "Meter Rent", meterRent);
            System.out.printf("%-25s : Rs. %d%n", "Service Tax", serviceTax);
            System.out.printf("%-25s : Rs. %d%n", "Other Charges", otherCharges);
            System.out.printf("%-25s : Rs. %d%n", "Surcharge", surcharge);
            ConsoleHelper.printDivider();
            System.out.printf("%-25s : Rs. %d%n", "TOTAL AMOUNT", totalAmount);
            ConsoleHelper.printDivider();

            // Save to DB
            conn c = new conn();
            PreparedStatement ps = c.c.prepareStatement(
                "INSERT INTO bill (meter_number, month, units, amount, bill_date) VALUES (?, ?, ?, ?, CURRENT_DATE)"
            );
            ps.setString(1, meter);
            ps.setString(2, month);
            ps.setInt(3, units);
            ps.setInt(4, totalAmount);
            ps.executeUpdate();

            ConsoleHelper.printSuccess("Bill calculated and stored for " + month + "!");

        } catch (NumberFormatException e) {
            ConsoleHelper.printError("Please enter valid numeric units.");
        } catch (Exception e) {
            ConsoleHelper.printError("Database error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new calculate_bill().calculateAndStoreBill();
    }
}
