public class Login {
    //Instance fields
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

    //Method that checks the username
    public boolean checkUserName() {
        return UserName.contains("_") && UserName.length() <= 5;
    }

    //Method that check that the password matches the requirements
    public boolean checkPasswordComplexity() {
        return Password.length() >= 8 && Password.matches(".*[A-Z].*") &&
                Password.matches(".*[0-9].*") &&
                Password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    //Method checks if the phone number specifically begins with +27 and is no longer than 12 characters which should only be digits
    public boolean checkCellPhoneNumber() {
        // Must start with +27
        if (!PhoneNum.startsWith("+27")) {
            return false;
        }
        // Max length 12 (+27 plus up to 9 digits)
        if (PhoneNum.length() > 12) {
            return false;
        }
        // Check that everything after +27 are digits
        String numberPart = PhoneNum.substring(3);
        return numberPart.matches("\\d+");
    }

    //Method to log the user in
    public boolean loginUser(String newUser, String newPassword) {
        return this.UserName.equals(newUser) && this.Password.equals(newPassword);
    }

    //Method to return the login status
    public boolean returnLoginStatus(String newUser, String newPassword) {
        if (loginUser(newUser, newPassword)) {
            System.out.println(
                    "Welcome, " + FName + " " + SName + ". It is great to see you again.\n" +
                            "User Details:\nName: " + FName + " " + SName +
                            "\nUsername: " + UserName + "\nPhone: " + PhoneNum
            );
            return true;
        } else {
            System.out.println("Username or password is incorrect.");
            return false;
        }
    }
}