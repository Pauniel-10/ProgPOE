import javax.swing.JOptionPane;
import java.util.Random;
import java.util.Scanner;

public class Message {
    private static int totalMessages = 0;

    // Instance fields
    private final String messageID;
    private final String recipient;
    private final String message;
    private final String messageHash;

    public Message(String messageID, String recipient, String message, String messageHash) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = messageHash;
    }

    // method to check the message ID
    public static boolean checkMessageID(String id) {
        return id.length() <= 10;
    }

    //Static method for the test
    public static String generateMessageID(String messageID) {
        if (checkMessageID(messageID)) {
            return "Message ID generated: " + messageID;
        } else {
            return "Invalid Message ID";
        }
    }

    // Method to check the Recipients Cell Phone number
    public static boolean checkRecipientCell(String cellNum) {
        return cellNum.matches("^\\+\\d{1,3}\\d{1,10}$");
    }

    //Static method for the test
    public static String checkRecipientCellTest(String cellNum) {
        if (checkRecipientCell(cellNum)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    //Method for creating the message hash
    public static String createMessageHash(String messageID, int numMessage, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        String hash = messageID.substring(0, 2) + ":" + numMessage + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // Method that handles send/store/disregard prompt and returns user choice as string
    public static String sentMessage(Scanner scanner) {
        while (true) {
            System.out.println("""
                    Select:
                    1) Send Message
                    2) Disregard Message
                    3) Store Message to send later""");
            System.out.print("Enter choice: ");
            String sendChoice = scanner.nextLine().trim();
            switch (sendChoice) {
                case "1":
                    return "Send";
                case "2":
                    return "Disregard";
                case "3":
                    return "Store";
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    // Static method for testing
    public static String sentMessageTest(String sendChoice) {
        return switch (sendChoice) {
            case "1" -> "Message successfully sent.";
            case "2" -> "Press 0 to delete message.";
            case "3" -> "Message successfully stored.";
            default -> "Invalid choice";
        };
    }

    // Method that shows message details in JOptionPane
    public void printMessages() {
        JOptionPane.showMessageDialog(null,
                "Message ID: " + messageID +
                        "\nMessage Hash: " + messageHash +
                        "\nRecipient: " + recipient +
                        "\nMessage: " + message
        );
    }

    // Method to return total number of messages
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // Method to store message
    public void storeMessage() {
        String json = "{\n" +
                "\"MessageID\": \"" + messageID + "\",\n" +
                "\"MessageHash\": \"" + messageHash + "\",\n" +
                "\"Recipient\": \"" + recipient + "\",\n" +
                "\"Message\": \"" + message + "\"\n" +
                "}";
        System.out.println(json);
    }

    // Main message sending workflow
    public static void startChat(Scanner reader) {
        boolean running = true; // Outer menu loop
        int totalMessagesSent = 0; // Total messages counter

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
                    // Ask how many messages to send this time
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

                    // Loop for sending messages
                    for (int i = 0; i < numToSend; i++) {
                        // Recipient validation
                        String recipient;
                        while (true) {
                            System.out.print("\nEnter recipient number (+CountryCode and number): ");
                            recipient = reader.nextLine().trim();
                            if (checkRecipientCell(recipient)) break;
                            System.out.println("Cell phone number incorrectly formatted. Please use + and up to 10 digits.");
                        }

                        // Message validation
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

                        // Generate message ID and hash
                        String messageID;
                        do {
                            messageID = String.valueOf(1000000000L + new Random().nextInt(900000000));
                        } while (!checkMessageID(messageID));

                        String hash = createMessageHash(messageID, totalMessagesSent, msg);

                        // Send/Store/Disregard
                        String action = sentMessage(reader);
                        if (action.equals("Send") || action.equals("Store")) {
                            totalMessages++;
                            totalMessagesSent++;
                            Message m = new Message(messageID, recipient, msg, hash);
                            m.printMessages();   // Pop-up
                            m.storeMessage();    // JSON in console
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
        //Print total number of messages sent
        System.out.println("Total messages sent: " + totalMessagesSent);
    }

    //Check the message length method for testing
    public static String checkMessageLength(String msg) {
        if (msg.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = msg.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
    }
}