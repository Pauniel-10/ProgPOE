import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Message {
    private static final int totalMessages = 0;

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

    // Method to check message ID format (for JUnit)
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

        try (FileWriter file = new FileWriter("messages.json", true)) { // 'true' means append mode
            file.write(json + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while storing the message: " + e.getMessage());
        }
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