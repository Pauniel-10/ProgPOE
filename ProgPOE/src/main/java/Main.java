import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // Registration
        Login user = Login.registerUser(reader);

        // Login
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter Username: ");
            String loginUsername = reader.nextLine();
            System.out.print("Enter Password: ");
            String loginPassword = reader.nextLine();

            if (user.returnLoginStatus(loginUsername, loginPassword)) {
                loggedIn = true;
            } else {
                System.out.println("Login failed. Try again.");
            }
        }

        // Welcome Message
        System.out.println("\nWelcome to QuickChat.");

        // Start QuickChat menu
        Message.startChat(reader);
    }
}