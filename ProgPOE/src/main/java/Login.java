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
        if (FName == null) System.exit(0);

        String SName = JOptionPane.showInputDialog("Enter Surname:");
        if (SName == null) System.exit(0);

        String UserName = JOptionPane.showInputDialog(
                "Enter Username:\n- Must contain '_'\n- Max 5 characters"
        );
        if (UserName == null) System.exit(0);

        String Password = JOptionPane.showInputDialog(
                "Enter Password:\n- Min 8 characters\n- 1 capital letter\n- 1 number\n- 1 special character"
        );
        if (Password == null) System.exit(0);

        String PhoneNum = JOptionPane.showInputDialog(
                "Enter Phone Number:\n- Must contain international code (e.g., +27)\n- Max 10 digits after code"
        );
        if (PhoneNum == null) System.exit(0);

        Login user = new Login(FName, SName, UserName, Password, PhoneNum);

        if (!user.checkUserName())
            JOptionPane.showMessageDialog(null,
                    "Username incorrectly formatted. Must contain '_' and max 5 chars.");
        if (!user.checkPasswordComplexity())
            JOptionPane.showMessageDialog(null,
                    "Password incorrectly formatted. Ensure complexity requirements.");
        if (!user.checkCellPhoneNumber())
            JOptionPane.showMessageDialog(null,
                    "Cell phone number incorrectly formatted.");
        else
            JOptionPane.showMessageDialog(null, "Registration successful, please login");

        return user;
    }

    public boolean loginUser(String newUser, String newPassword) {
        return this.UserName.equals(newUser) && this.Password.equals(newPassword);
    }

    public boolean returnLoginStatus(String newUser, String newPassword) {
        if (loginUser(newUser, newPassword)) {
            JOptionPane.showMessageDialog(null,
                    "Welcome, " + FName + " " + SName + ". It is great to see you again.\n" +
                            "User Details:\nName: " + FName + " " + SName +
                            "\nUsername: " + UserName + "\nPhone: " + PhoneNum);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Username or password is incorrect.");
            return false;
        }
    }

}
