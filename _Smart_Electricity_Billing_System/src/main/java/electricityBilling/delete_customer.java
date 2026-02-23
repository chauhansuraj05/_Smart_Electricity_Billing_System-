package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class delete_customer {

    private Scanner scanner;

    public delete_customer() {
        this.scanner = new Scanner(System.in);
    }

    public void deleteCustomer() {
        ConsoleHelper.printHeader("DELETE CUSTOMER");

        System.out.print("Enter Meter Number of customer to delete: ");
        String meter = scanner.nextLine().trim();

        try {
            conn c = new conn();

            // Show customer before deleting
            PreparedStatement ps1 = c.c.prepareStatement(
                "SELECT * FROM emp WHERE meter_number = ?"
            );
            ps1.setString(1, meter);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                ConsoleHelper.printError("No customer found with meter number: " + meter);
                return;
            }

            ConsoleHelper.printDivider();
            System.out.println("Customer to be deleted:");
            System.out.println("Name         : " + rs.getString("name"));
            System.out.println("Meter Number : " + rs.getString("meter_number"));
            System.out.println("Address      : " + rs.getString("address"));
            System.out.println("Phone        : " + rs.getString("phone"));
            ConsoleHelper.printDivider();

            System.out.print("Are you sure you want to delete this customer? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (!confirm.equals("yes")) {
                ConsoleHelper.printInfo("Deletion cancelled.");
                return;
            }

            
            PreparedStatement psDeleteBills = c.c.prepareStatement(
                "DELETE FROM bill WHERE meter_number = ?"
            );
            psDeleteBills.setString(1, meter);
            psDeleteBills.executeUpdate();

            // Delete customer
            PreparedStatement psDeleteEmp = c.c.prepareStatement(
                "DELETE FROM emp WHERE meter_number = ?"
            );
            psDeleteEmp.setString(1, meter);
            int deleted = psDeleteEmp.executeUpdate();

            if (deleted > 0) {
                ConsoleHelper.printSuccess("Customer and associated bills deleted successfully!");
            } else {
                ConsoleHelper.printError("Deletion failed. Please try again.");
            }

        } catch (Exception e) {
            ConsoleHelper.printError("Error during deletion: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new delete_customer().deleteCustomer();
    }
}
