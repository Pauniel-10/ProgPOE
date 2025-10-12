import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // Calls the reisterUser() method from Login class
        Login user = Login.registerUser(reader);

        // A loop that will keep looping until the correct login details are entered
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("\n====== Login here =======");
            System.out.print("\nEnter Username: ");
            String loginUsername = reader.nextLine();
            System.out.print("Enter Password: ");
            String loginPassword = reader.nextLine();

            //Validates login details using the returnLoginStatus() method
            if (user.returnLoginStatus(loginUsername, loginPassword)) {
                loggedIn = true; //Loop breaks when the login is successfull
            } else {
                System.out.println("Login failed. Try again.");
            }
        }

        // Welcome Message is displayed after user is successfully logged in
        System.out.println("\nWelcome to QuickChat.");

        // Calls the startChat() method from the message class after the welcome message is shown
        Message.startChat(reader);
    }
}