import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // Registration
        Login user = Login.registerUser();

        // Login check
        if (user.checkUserName() && user.checkPasswordComplexity() && user.checkCellPhoneNumber()) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter Username: ");
            String loginUsername = reader.nextLine();
            System.out.print("Enter Password: ");
            String loginPassword = reader.nextLine();

            if (user.loginUser(loginUsername, loginPassword)) {
                System.out.println("\nWelcome to Messages " + user.getFName() );

                boolean running = true;
                while (running) {
                    System.out.println("\nSelect an option:");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show Recently Sent Messages (Coming Soon)");
                    System.out.println("3) Quit");

                    String option = reader.nextLine();

                    if (option.equals("1")) {
                        // Send Messages
                        int numMessages;
                        System.out.print("How many messages would you like to send? ");
                        while (true) {
                            String input = reader.nextLine();
                            boolean validNumber = true;
                            for (int j = 0; j < input.length(); j++) {
                                if (!Character.isDigit(input.charAt(j))) {
                                    validNumber = false;
                                    break;
                                }
                            }
                            if (validNumber && !input.isEmpty()) {
                                numMessages = Integer.parseInt(input);
                                if (numMessages > 0) break;
                            }
                            System.out.print("Please enter a valid positive number: ");
                        }

                        // Initialize message storage
                        message.initMessages(numMessages);

                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("\n--- Message " + (i + 1) + " ---");

                            // Recipient
                            String recipient;
                            while (true) {
                                System.out.print("Enter recipient number (with international code, max 10 digits): ");
                                recipient = reader.nextLine();
                                message temp = new message(i + 1, recipient, "");
                                if (temp.checkRecipientCell()) {
                                    System.out.println("Cell phone number successfully captured.");
                                    break;
                                } else {
                                    System.out.println("Cell phone number is incorrectly formatted. Please try again.");
                                }
                            }

                            // Message content
                            String content;
                            while (true) {
                                System.out.print("Enter your message (max 250 characters): ");
                                content = reader.nextLine();
                                if (content.length() <= 250) {
                                    System.out.println("Message ready to send.");
                                    break;
                                } else {
                                    System.out.println("Message exceeds 250 characters by " + (content.length() - 250) + ", please reduce size.");
                                }
                            }

                            // Create message object
                            message msg = new message(i + 1, recipient, content);

                            // Send / Discard / Store
                            String result = msg.sendMessage();
                            System.out.println(result);

                            // Display message details
                            msg.printMessageDetails();
                        }

                        System.out.println("Total messages sent: " + message.returnTotalMessages());

                    } else if (option.equals("2")) {
                        System.out.println("Coming Soon.");

                    } else if (option.equals("3")) {
                        System.out.println("Exiting Quickchat. Goodbye!");
                        running = false;

                    } else {
                        System.out.println("Invalid option. Please select 1, 2, or 3.");
                    }
                }

            } else {
                System.out.println("Username or password is incorrect. Login failed.");
            }
        }
    }
}
