import java.util.Scanner;

public class message {

    private long messageID;
    private int messageNumber;
    private String recipient;
    private String content;
    private String messageHash;

    private static int totalMessages = 0;          // Total messages sent
    private static message[] sentMessages;         // Array to store messages
    private static int messagesStored = 0;         // Index for storing messages

    // Constructor
    public message(int messageNumber, String recipient, String content) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.content = content;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generate a random 10-digit message ID using Math.random()
    private long generateMessageID() {
        return (long)(Math.random() * 9_000_000_000L) + 1_000_000_000L;
    }

    // Validate message ID (10 digits)
    public boolean checkMessageID() {
        return String.valueOf(messageID).length() == 10;
    }

    // Validate recipient number (must start with '+' and <= 13 digits including country code)
    public boolean checkRecipientCell() {
        return recipient.matches("^\\+\\d{1,3}\\d{1,10}$");
    }

    // Validate message length (<= 250 characters)
    public boolean checkMessageLength() {
        return content.length() <= 250;
    }

    // Create message hash: firstTwoDigitsOfID:number:firstAndLastWordInMessage (all caps)
    public String createMessageHash() {
        String idStr = String.valueOf(messageID);
        String firstTwo = idStr.substring(0, 2);

        String[] words = content.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Send/Discard/Store message (no switch/case)
    public String sendMessage() {
        Scanner reader = new Scanner(System.in);
        System.out.println("\nSelect option for this message:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");

        int choice = reader.nextInt();
        reader.nextLine(); // Consume newline

        if (choice == 1) {
            storeSentMessage(this);
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete message.";
        } else if (choice == 3) {
            // Placeholder: storeMessageToJSON would be called here if we were saving to a file
            System.out.println("Message successfully stored (JSON storage placeholder).");
            return "Message successfully stored.";
        } else {
            return "Invalid option. Message not sent.";
        }
    }

    // Print message details to console
    public void printMessageDetails() {
        System.out.println("\nMessage Details:");
        System.out.println("Message ID: " + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + content);
        System.out.println("-----------------------------");
    }

    // Store sent messages in runtime array
    private static void storeSentMessage(message msg) {
        if (sentMessages == null) return;
        if (messagesStored < sentMessages.length) {
            sentMessages[messagesStored] = msg;
            messagesStored++;
            totalMessages++;
        }
    }

    // Return total messages sent
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // Print all sent messages
    public static void printAllMessages() {
        if (sentMessages == null || messagesStored == 0) {
            System.out.println("No messages have been sent yet.");
            return;
        }
        for (int i = 0; i < messagesStored; i++) {
            message m = sentMessages[i];
            System.out.println("Message " + (i + 1) + ":");
            System.out.println("ID: " + m.messageID);
            System.out.println("Hash: " + m.messageHash);
            System.out.println("Recipient: " + m.recipient);
            System.out.println("Message: " + m.content);
            System.out.println("---------------------------");
        }
    }

    // Initialize message array with capacity (based on number of messages user wants to send)
    public static void initMessages(int capacity) {
        sentMessages = new message[capacity];
    }

    // Getters
    public long getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public String getMessageHash() { return messageHash; }

    // Placeholder for storing message to JSON
    // This is where you would implement actual JSON storage if needed
    public void storeMessageToJSON(message msg) {
        // Example:
        // Write to a file here
        // This method is intentionally left as a placeholder
    }
}
