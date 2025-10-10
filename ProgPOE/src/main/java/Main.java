import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {

        // Register user via GUI
        Login user = Login.registerUser();

        // Login via GUI
        String loginUsername = JOptionPane.showInputDialog("Enter Username:");
        String loginPassword = JOptionPane.showInputDialog("Enter Password:");

        if (user.returnLoginStatus(loginUsername, loginPassword)) {
            Message.startChat(); // Launch Quickchat
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Please try again later.");
        }
    }
}
