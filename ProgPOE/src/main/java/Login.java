import java.util.Scanner;

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

    public static Login registerUser(Scanner reader) {
        while (true) {
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

            boolean valid = true;
            if (!user.checkUserName()) {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
                valid = false;
            }
            if (!user.checkPasswordComplexity()) {
                System.out.println(" Password is not correctly formatted, please ensure that the password is at least eight characters, a capital letter, a number, and a special character.");
                valid = false;
            }
            if (!user.checkCellPhoneNumber()) {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
                valid = false;
            }
            if (valid) {
                System.out.println("Registration successful, please login");
                return user;
            } else {
                System.out.println("Registration failed. Please try again.\n");
            }
        }
    }

    public boolean loginUser(String newUser, String newPassword) {
        return this.UserName.equals(newUser) && this.Password.equals(newPassword);
    }

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