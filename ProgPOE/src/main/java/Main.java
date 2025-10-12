import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        Login user = null;
        boolean registered = false;

        // Implemented the registration in the main class instead of the login class
        while (!registered) {
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

            user = new Login(FName, SName, UserName, Password, PhoneNum);

            if (!user.checkUserName()) {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            } else if (!user.checkPasswordComplexity()) {
                System.out.println(" Password is not correctly formatted, please ensure that the password is at least eight characters, a capital letter, a number, and a special character.");
            } else if (!user.checkCellPhoneNumber()) {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            } else {
                System.out.println("Registration successful, please login");
                registered = true;
            }
        }

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

        //Message workflow

        boolean running = true; // Keeps the program running until the user chooses to quit
        int totalMessagesSent = 0; // Counter for total number of messages sent

        //Main loop
        while (running) {
            System.out.println("""
                    Select an option:
                    1) Send Messages
                    2) Show recently sent messages
                    3) Quit""");
            System.out.print("Enter choice: ");
            String option = reader.nextLine().trim();

            switch (option) {
                case "1" -> {
                    int numToSend;
                    while (true) {
                        System.out.print("\nHow many messages would you like to send? ");
                        try {
                            numToSend = Integer.parseInt(reader.nextLine());
                            if (numToSend > 0) break;
                            else System.out.println("Please enter a number greater than 0.");
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                        }
                    }

                    for (int i = 0; i < numToSend; i++) {
                        String recipient;
                        while (true) {
                            System.out.print("\nEnter recipient number (+CountryCode and number): ");
                            recipient = reader.nextLine().trim();
                            if (Message.checkRecipientCell(recipient)) break;
                            System.out.println("Cell phone number incorrectly formatted. Please use + and up to 10 digits.");
                        }

                        String msg;
                        while (true) {
                            System.out.print("Enter your message (max 250 chars): ");
                            msg = reader.nextLine();
                            if (msg.length() <= 250) {
                                System.out.println("Message ready to send");
                                break;
                            } else {
                                System.out.println("Please enter a message of less than 250 characters.");
                            }
                        }

                        String messageID;
                        do {
                            messageID = String.valueOf(1000000000L + new Random().nextInt(900000000));
                        } while (!Message.checkMessageID(messageID));

                        String hash = Message.createMessageHash(messageID, totalMessagesSent, msg);

                        String action = Message.sentMessage(reader);
                        if (action.equals("Send") || action.equals("Store")) {
                            totalMessagesSent++;
                            Message m = new Message(messageID, recipient, msg, hash);
                            m.printMessages();
                            m.storeMessage();
                        } else {
                            System.out.println("Message disregarded.");
                        }
                    }
                }
                case "2" -> System.out.println("Coming Soon.");
                case "3" -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Total messages sent: " + totalMessagesSent);
        reader.close();
    }
}