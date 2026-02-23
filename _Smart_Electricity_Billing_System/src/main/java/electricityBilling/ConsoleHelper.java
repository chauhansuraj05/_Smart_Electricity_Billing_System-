package electricityBilling;

public class ConsoleHelper {

    public static void printHeader(String title) {
        System.out.println("\n========================================");
        System.out.println("   " + title);
        System.out.println("========================================");
    }

    public static void printDivider() {
        System.out.println("----------------------------------------");
    }

    public static void printSuccess(String msg) {
        System.out.println("[SUCCESS] " + msg);
    }

    public static void printError(String msg) {
        System.out.println("[ERROR] " + msg);
    }

    public static void printInfo(String msg) {
        System.out.println("[INFO] " + msg);
    }
}
