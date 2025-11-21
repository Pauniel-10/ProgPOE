import javax.swing.JOptionPane;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        Login user = null;
        boolean registered = false;

        // Registration
        while (!registered) {
            System.out.println("====== Register here =======");
            System.out.print("Enter First Name: ");
            String FName = reader.nextLine();
            System.out.print("Enter Surname: ");
            String SName = reader.nextLine();
            System.out.println("------------------------------------------------\n -Username must contain '_'\n -Username must be no longer than 5 characters\n------------------------------------------------");
            System.out.print("Enter Username: ");
            String UserName = reader.nextLine();
            System.out.println("------------------------------------------------\n -Password must be at least 8 characters long\n -Password must contain a capital letter\n -Password must contain a number\n -Password must contain a special character\n------------------------------------------------");
            System.out.print("Enter Password: ");
            String Password = reader.nextLine();
            System.out.println("------------------------------------------------\n -Phone number must contain international code, eg +27\n -No more than 10 characters for the national part\n------------------------------------------------");
            System.out.print("Enter phone number: ");
            String PhoneNum = reader.nextLine();

            user = new Login(FName, SName, UserName, Password, PhoneNum);

            if (!user.checkUserName()) {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            } else if (!user.checkPasswordComplexity()) {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            } else if (!user.checkCellPhoneNumber()) {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            } else {
                System.out.println("Registration successful, please login");
                registered = true;
            }
        }

        // Login
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("\n====== Login here =======\n");
            System.out.print("Enter Username: ");
            String loginUsername = reader.nextLine();
            System.out.print("Enter Password: ");
            String loginPassword = reader.nextLine();

            if (user.returnLoginStatus(loginUsername, loginPassword)) {
                loggedIn = true;
            } else {
                System.out.println("Login failed. Try again.");
            }
        }

        // After login QuickChat opens
        MessageService service = new MessageService();
        service.loadStoredMessagesFromFile(); // preload stored messages if any

        boolean running = true;
        int totalMessagesSent = 0;
        Random rnd = new Random();

        while (running) {
            String menu = "Welcome to QuickChat.\n\nSelect an option by entering the number:\n1) Send Messages\n2) Show recently sent messages\n3) Quit";
            String optionInput = JOptionPane.showInputDialog(null, menu, "QuickChat Menu", JOptionPane.QUESTION_MESSAGE);
            if (optionInput == null) optionInput = "3"; // cancel => quit
            String option = optionInput.trim();

            switch (option) {
                case "1" -> {
                    int numToSend;
                    while (true) {
                        String numStr = JOptionPane.showInputDialog(null, "How many messages would you like to send?", "Number of Messages", JOptionPane.QUESTION_MESSAGE);
                        if (numStr == null) { numToSend = 0; break; } // cancel go back
                        try {
                            numToSend = Integer.parseInt(numStr.trim());
                            if (numToSend > 0) break;
                            JOptionPane.showMessageDialog(null, "Please enter a number greater than 0.", "Invalid Number", JOptionPane.WARNING_MESSAGE);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                        }
                    }

                    for (int i = 0; i < numToSend; i++) {
                        // Recipient input
                        String recipient;
                        while (true) {
                            String recInput = JOptionPane.showInputDialog(null, "Enter recipient number (+CountryCode and number):", "Recipient Entry", JOptionPane.QUESTION_MESSAGE);
                            if (recInput == null) { recipient = null; break; } // cancel -> back to menu
                            if (Message.checkRecipientCell(recInput.trim())) { recipient = recInput.trim(); break; }
                            JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted. Use + and up to 10 digits for national number.", "Invalid Number", JOptionPane.ERROR_MESSAGE);
                        }
                        if (recipient == null) break;

                        // Message input
                        String msg;
                        while (true) {
                            String msgInput = JOptionPane.showInputDialog(null, "Enter your message (max 250 chars):", "Message Entry", JOptionPane.QUESTION_MESSAGE);
                            if (msgInput == null) { msg = null; break; } // cancel -> back to menu
                            if (msgInput.length() <= 250) { msg = msgInput; break; }
                            int excess = msgInput.length() - 250;
                            JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + excess + ", please reduce size.", "Too Long", JOptionPane.ERROR_MESSAGE);
                        }
                        if (msg == null) break;

                        // Generate 10-digit message ID
                        String messageID;
                        do {
                            long rand = Math.abs(rnd.nextLong() % 1_000_000_0000L);
                            messageID = String.format("%010d", rand);
                        } while (!Message.checkMessageID(messageID));

                        // Use the loop index i as the message number in the hash
                        String hash = Message.createMessageHash(messageID, i, msg);

                        // Send/Store/Disregard
                        String action = Message.sentMessageDialog();
                        Message m = new Message(messageID, user.getPhoneNum(), recipient, msg, hash);

                        if ("Send".equals(action)) {
                            // store and increment before showing details
                            m.storeMessage();
                            service.addMessage(m, "Sent");
                            totalMessagesSent++;
                            // Show full details
                            m.printMessages();
                            System.out.println("Message successfully sent.");
                        } else if ("Store".equals(action)) {
                            m.storeMessage();
                            service.addMessage(m, "Stored");
                            System.out.println("Message successfully stored.");
                        } else {
                            service.addMessage(m, "Disregard");
                            System.out.println("Message disregarded.");
                        }
                    }

                    // After the full batch of messages has been processed, display total
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent, "Sent Summary", JOptionPane.INFORMATION_MESSAGE);
                }
                case "2" -> JOptionPane.showMessageDialog(null, "Coming Soon.", "Recently Sent Messages", JOptionPane.INFORMATION_MESSAGE);
                case "3" -> running = false;
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please enter 1, 2 or 3.", "Invalid Choice", JOptionPane.WARNING_MESSAGE);
            }
        }

        // Final summary
        System.out.println("\n--- QuickChat Session End ---");
        System.out.println("Total messages sent (session): " + totalMessagesSent);

        // display report
        String[] senderRecipients = service.displaySenderRecipientOfSent();
        if (senderRecipients.length == 0) {
            System.out.println("No sent messages to display sender/recipient.");
        } else {
            System.out.println("\nSent messages (Sender -> Recipient):");
            for (String senderRecipient : senderRecipients) System.out.println(senderRecipient);
        }

        String longest = service.getLongestSentMessage();
        if (longest != null) {
            System.out.println("\nLongest sent message:\n" + longest);
        } else {
            System.out.println("\nNo sent messages available to determine longest message.");
        }

        String report = service.generateReport();
        if (report.isEmpty()) {
            System.out.println("\nNo sent messages to report.");
        } else {
            System.out.println("\n" + report);
        }

        System.out.println("\nEnd of session. Goodbye.");
        reader.close();
    }
}