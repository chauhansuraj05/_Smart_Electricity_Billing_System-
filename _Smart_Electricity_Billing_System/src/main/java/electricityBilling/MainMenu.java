package electricityBilling;

import java.util.Scanner;

public class MainMenu {

    private Scanner scanner;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("\n");
        ConsoleHelper.printHeader("ELECTRICITY BILLING SYSTEM - MAIN MENU");

        System.out.println("  --- MASTER ---");
        System.out.println("  1. New Customer");
        System.out.println("  2. Customer Details");

        System.out.println("\n  --- USER ---");
        System.out.println("  3. Calculate Bill");
        System.out.println("  4. Pay Bill");
        System.out.println("  5. Last Bill");

        System.out.println("\n  --- REPORT ---");
        System.out.println("  6. Generate Bill");

        System.out.println("\n  --- ADMIN ---");
        System.out.println("  7. Update Customer");
        System.out.println("  8. Delete Customer");

        System.out.println("\n  9. Exit");
        ConsoleHelper.printDivider();
        System.out.print("  Enter your choice: ");
    }

    public void start() {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    new new_customer().addCustomer();
                    break;
                case "2":
                    new customer_details().run();
                    break;
                case "3":
                    new calculate_bill().calculateAndStoreBill();
                    break;
                case "4":
                    new pay_bill().payBill();
                    break;
                case "5":
                    new LastBill().viewLastBill();
                    break;
                case "6":
                    new generate_bill().generateBill();
                    break;
                case "7":
                    new update_customer().updateCustomer();
                    break;
                case "8":
                    new delete_customer().deleteCustomer();
                    break;
                case "9":
                    ConsoleHelper.printInfo("Thank you for using Electricity Billing System. Goodbye!");
                    running = false;
                    break;
                default:
                    ConsoleHelper.printError("Invalid choice. Please enter a number between 1-9.");
            }
        }
    }

    public static void main(String[] args) {
        
        login loginPage = new login();
        boolean success = false;
        int attempts = 0;

        while (!success && attempts < 3) {
            success = loginPage.authenticate();
            attempts++;
            if (!success && attempts < 3) {
                System.out.println("Attempts remaining: " + (3 - attempts));
            }
        }

        if (success) {
            new MainMenu().start();
        } else {
            ConsoleHelper.printError("Too many failed login attempts. Exiting.");
            System.exit(0);
        }
    }
}
