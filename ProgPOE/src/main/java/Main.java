import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {

        // Registration
        Login user = Login.registerUser();

        // Login
        String loginUsername = JOptionPane.showInputDialog("Enter Username:");
        if (loginUsername == null) System.exit(0);

        String loginPassword = JOptionPane.showInputDialog("Enter Password:");
        if (loginPassword == null) System.exit(0);

        if (user.returnLoginStatus(loginUsername, loginPassword)) {
            Message.startChat(); // Start Quickchat if login succeeds
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Exiting program.");
            System.exit(0);
        }
    }
}
