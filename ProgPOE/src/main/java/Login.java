import javax.swing.JOptionPane;

public class Login {
    private final String FName;
    private final String SName;
    private final String UserName;
    private final String Password;
    private final String PhoneNum;

    public Login(String FName, String SName, String UserName, String Password, String PhoneNum) {
        this.FName = FName;
        this.SName = SName;
        this.UserName = UserName;
        this.Password = Password;
        this.PhoneNum = PhoneNum;
    }

    public boolean checkUserName() {
        return UserName.contains("_") && UserName.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        return Password.length() >= 8 && Password.matches(".*[A-Z].*") &&
                Password.matches(".*[0-9].*") &&
                Password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    public boolean checkCellPhoneNumber() {
        return PhoneNum.matches("^\\+\\d{1,3}\\d{1,10}$");
    }

    public static Login registerUser() {
        String FName = JOptionPane.showInputDialog("Enter First Name:");
        String SName = JOptionPane.showInputDialog("Enter Surname:");
        String UserName = JOptionPane.showInputDialog(
                "Enter Username:\n- Must contain '_'\n- Max 5 characters");
        String Password = JOptionPane.showInputDialog(
                "Enter Password:\n- Min 8 characters\n- Capital letter\n- Number\n- Special character");
        String PhoneNum = JOptionPane.showInputDialog(
                "Enter phone number:\n- Include international code, e.g., +27\n- Max 10 digits");

        Login user = new Login(FName, SName, UserName, Password, PhoneNum);

        StringBuilder errors = new StringBuilder();
        if (!user.checkUserName()) errors.append("Username incorrectly formatted.\n");
        if (!user.checkPasswordComplexity()) errors.append("Password incorrectly formatted.\n");
        if (!user.checkCellPhoneNumber()) errors.append("Cell phone number incorrectly formatted.\n");

        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(null, errors.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Registration successful, please login.");
        }

        return user;
    }

    public boolean loginUser(String newUser, String newPassword) {
        return this.UserName.equals(newUser) && this.Password.equals(newPassword);
    }

    public boolean returnLoginStatus(String newUser, String newPassword) {
        if (loginUser(newUser, newPassword)) {
            JOptionPane.showMessageDialog(null, "Welcome, " + FName + " " + SName + ". It is great to see you again.\n\nUser Details:\nName: " + FName + " " + SName + "\nUsername: " + UserName + "\nPhone Number: " + PhoneNum);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Username or password is incorrect, please try again.");
            return false;
        }
    }

}
