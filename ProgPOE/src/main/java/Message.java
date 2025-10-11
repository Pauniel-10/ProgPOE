import javax.swing.JOptionPane;
import java.util.Random;
import java.util.Scanner;

public class Message {
    private static int totalMessages = 0;

    // Instance fields for message info
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

    // 1. checkMessageID()
    public static boolean checkMessageID(String id) {
        return id.length() <= 10;
    }

    // 2. checkRecipientCell()
    public static boolean checkRecipientCell(String cellNum) {
        return cellNum.matches("^\\+\\d{1,3}\\d{1,10}$");
    }

    // 3. createMessageHash()
    public static String createMessageHash(String messageID, int numMessage, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        String hash = messageID.substring(0, 2) + ":" + numMessage + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // 4. sentMessage(): Handles send/store/disregard prompt and returns user choice as string
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

    // 5. printMessages(): shows message details in JOptionPane
    public void printMessages() {
        JOptionPane.showMessageDialog(null,
                "Message ID: " + messageID +
                        "\nMessage Hash: " + messageHash +
                        "\nRecipient: " + recipient +
                        "\nMessage: " + message
        );
    }

    // 6. returnTotalMessages()
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // 7. storeMessage(): print json to console
    public void storeMessage() {
        String json = "{\n" +
                "\"MessageID\": \"" + messageID + "\",\n" +
                "\"MessageHash\": \"" + messageHash + "\",\n" +
                "\"Recipient\": \"" + recipient + "\",\n" +
                "\"Message\": \"" + message + "\"\n" +
                "}";
        System.out.println(json);
    }

    // 8. Main message sending workflow
    public static void startChat(Scanner scanner) {
        // 5. Ask number of messages
        int numMessages;
        while (true) {
            try {
                System.out.print("How many messages would you like to send? ");
                numMessages = Integer.parseInt(scanner.nextLine());
                if (numMessages > 0) break;
                else System.out.println("Please enter a number greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        int messagesSent = 0;
        boolean running = true;
        while (running) {
            System.out.println("""
                    Select an option:
                    1) Send Messages
                    2) Show recently sent messages
                    3) Quit""");
            System.out.print("Enter choice: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> {
                    while (messagesSent < numMessages) {
                        // Recipient with validation
                        String recipient;
                        while (true) {
                            System.out.print("Enter recipient number (+CountryCode and number, max 10 digits after +): ");
                            recipient = scanner.nextLine().trim();
                            if (checkRecipientCell(recipient)) {
                                break;
                            } else {
                                System.out.println("Cell phone number incorrectly formatted. Please use + and up to 10 digits.");
                            }
                        }

                        // Message with validation
                        String msg;
                        while (true) {
                            System.out.print("Enter your message (max 250 chars): ");
                            msg = scanner.nextLine();
                            if (msg.length() > 250) {
                                System.out.println("Please enter a message of less than 250 characters.");
                            } else {
                                System.out.println("Message sent");
                                break;
                            }
                        }

                        // Message ID, message number, hash
                        String messageID;
                        do {
                            messageID = String.valueOf(1000000000L + new Random().nextInt(900000000));
                        } while (!checkMessageID(messageID));

                        int msgNum = messagesSent;
                        String hash = createMessageHash(messageID, msgNum, msg);

                        // Send/Store/Disregard
                        String action = sentMessage(scanner);
                        if (action.equals("Send") || action.equals("Store")) {
                            totalMessages++;
                            Message m = new Message(messageID, recipient, msg, hash);
                            m.printMessages();      // Pop-up
                            m.storeMessage();       // JSON in console
                        } else {
                            System.out.println("Message disregarded.");
                        }
                        messagesSent++;
                    }
                }
                case "2" -> System.out.println("Coming Soon.");
                case "3" -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Total messages sent: " + returnTotalMessages());
    }
}