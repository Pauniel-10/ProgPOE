import java.util.Scanner;

//Instance attributes
public class Login {
    private String FName;
    private String SName;
    private String UserName;
    private String Password;
    private String PhoneNum;

    //Constructors
    public Login(String FName, String SName, String UserName, String Password, String PhoneNum) {
        this.FName = FName;
        this.SName = SName;
        this.UserName = UserName;
        this.Password = Password;
        this.PhoneNum = PhoneNum;
    }

    //Method to check if the username is correct containing "_" and no longer than 5
    public boolean checkUserName() {
        return UserName.contains("_") && UserName.length() <= 5;
    }

    //Method to check if the password is correct according to the complexity
    public boolean checkPasswordComplexity() {
        return Password.length() >= 8 && Password.matches(".*[A-Z].*") &&
                Password.matches(".*[0-9].*") &&
                Password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    //Method to check the phone number
    public boolean checkCellPhoneNumber() {

        return PhoneNum.matches("^\\+\\d{1,3}\\d{1,10}$");
    }

    //Static method to handle the registration and the error messages
    public static Login registerUser() {
        Scanner reader = new Scanner(System.in);
        // User registers account
        System.out.println("====== Register here =======");
        System.out.println("Enter First Name: ");
        String FName = reader.nextLine();
        System.out.println("Enter Surname: ");
        String SName = reader.nextLine();
        System.out.println("------------------------------------------------\n -Username must contain '_'\n -Username must be no longer than 5 characters\n------------------------------------------------");
        System.out.println("Enter Username: ");
        String UserName = reader.nextLine();
        System.out.println("------------------------------------------------\n -Password must be at least 8 characters long\n -Password must contain a capital letter\n -Password must contain a number\n -Password must contain a special character\n------------------------------------------------");
        System.out.println("Enter Password: ");
        String Password = reader.nextLine();
        System.out.println("------------------------------------------------\n -Phone number must contain international code, eg +27\n -No more than 10 characters long\n------------------------------------------------");
        System.out.println("Enter phone number: ");
        String PhoneNum = reader.nextLine();

        Login user = new Login(FName, SName, UserName, Password, PhoneNum);

        if (!user.checkUserName()) {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
        }
        if (!user.checkPasswordComplexity()) {
            System.out.println("Password is not correctly formatted, please ensure that the password is at least eight characters, a capital letter, a number, and a special character.");

        }
        if (!user.checkCellPhoneNumber()) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        } else {
            System.out.println("Registration successful, please login");
        }
        return user;
    }

    //method to log the user in
    public boolean loginUser(String newUser, String newPassword) {
        return this.UserName.equals(newUser) && this.Password.equals(newPassword);
    }

    //Method to return the login status and the user's details
    public String returnLoginStatus(String newUser, String newPassword) {
        if (loginUser(newUser, newPassword)) {
            System.out.println("Welcome, " + FName + " " + SName + ". It is great to see you again.");
            System.out.println("\nUser Details: \n Name: " + FName + " " + SName + "\n Username: " + UserName + "\n Phone Number: " + PhoneNum);

        } else {
            System.out.println("Username or password is incorrect, please try again.");
        }
        return "\nWhat would you like to do today? ";
    }
}
