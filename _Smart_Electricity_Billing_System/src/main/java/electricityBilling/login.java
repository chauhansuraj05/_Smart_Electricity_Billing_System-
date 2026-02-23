package electricityBilling;

import java.sql.*;
import java.util.Scanner;

public class login {

    private Scanner scanner;

    // Hardcoded credentials
    private static final String HARDCODED_USERNAME = "suraj";
    private static final String HARDCODED_PASSWORD = "suraj@123";

    public login() {
        this.scanner = new Scanner(System.in);
    }

    public boolean authenticate() {
        ConsoleHelper.printHeader("ELECTRICITY BILLING SYSTEM - LOGIN");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

       
        if (username.equals(HARDCODED_USERNAME) && password.equals(HARDCODED_PASSWORD)) {
            ConsoleHelper.printSuccess("Login successful! Welcome, " + username);
            return true;
        }

        // If hardcoded fails, check database
        try {
            conn c1 = new conn();
            PreparedStatement ps = c1.c.prepareStatement(
                "SELECT * FROM login WHERE username = ? AND password = ?"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ConsoleHelper.printSuccess("Login successful! Welcome, " + username);
                return true;
            } else {
                ConsoleHelper.printError("Invalid username or password. Please try again.");
                return false;
            }

        } catch (Exception e) {
            ConsoleHelper.printError("Database error: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        login l = new login();
        boolean success = false;
        int attempts = 0;

        while (!success && attempts < 3) {
            success = l.authenticate();
            attempts++;
            if (!success && attempts < 3) {
                System.out.println("Attempts remaining: " + (3 - attempts));
            }
        }

        if (success) {
            new MainMenu().start();
        } else {
            ConsoleHelper.printError("Too many failed attempts. Exiting.");
            System.exit(0);
        }
    }
}