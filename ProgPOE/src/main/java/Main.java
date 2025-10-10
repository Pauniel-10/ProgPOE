import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        // Call static method for registration
        Login user = Login.registerUser();

        // Proceed only if user details are valid
        if (user.checkUserName() && user.checkPasswordComplexity() && user.checkCellPhoneNumber()) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter Username: ");
            String loginUsername = reader.nextLine();
            System.out.print("Enter Password: ");
            String loginPassword = reader.nextLine();

            if (user.loginUser(loginUsername, loginPassword)) {
                System.out.println("Welcome, " + loginUsername + "! You are now logged in.");
                message.chat();
            } else {
                System.out.println("Invalid login. Exiting program.");
            }
        }
    }
}
