import javax.swing.JOptionPane;
import java.util.Random;
import javax.swing.JTextArea;

public class Message {

    private final String messageID;
    private static int totalMessages = 0;

    public Message(String messageID) {
        this.messageID = messageID;
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public static String checkRecipientCell(String cellNum) {
        if (cellNum.startsWith("+") && cellNum.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public static String createMessageHash(String messageID, int numMessage, String message) {
        String[] words = message.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return (messageID.substring(0, 2) + ":" + numMessage + ":" + firstWord + lastWord).toUpperCase();
    }

    public static String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int exceed = message.length() - 250;
            return "Message exceeds 250 characters by " + exceed + ", please reduce size.";
        }
    }

    public static String sendMessage(String choice) {
        if (choice.equalsIgnoreCase("1")) {
            totalMessages++;
            return "Message successfully sent.";
        } else if (choice.equalsIgnoreCase("2")) {
            return "Press 0 to delete message.";
        } else if (choice.equalsIgnoreCase("3")) {
            return "Message successfully stored.";
        } else {
            return "Invalid choice.";
        }
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    // --- 7️⃣ Print message details ---
    public static void printMessages(String messageID, String messageHash, String recipient, String message) {
        JTextArea textArea = new JTextArea(
                "Message ID: " + messageID +
                        "\nMessage Hash: " + messageHash +
                        "\nRecipient: " + recipient +
                        "\nMessage: " + message
        );
        textArea.setColumns(30);      // width of the popup
        textArea.setRows(6);          // height of the popup
        textArea.setLineWrap(true);   // wrap lines automatically
        textArea.setWrapStyleWord(true); // wrap at word boundaries
        JOptionPane.showMessageDialog(null, textArea);
    }


    // Store Message in JSON placeholder
    public static void storeMessage(String[][] allMessages, int index, String messageID, String messageHash, String recipient, String message) {
        allMessages[index][0] = messageID;
        allMessages[index][1] = messageHash;
        allMessages[index][2] = recipient;
        allMessages[index][3] = message;

        String json = "{\n\"MessageID\": \"" + messageID + "\",\n\"MessageHash\": \"" + messageHash +
                "\",\n\"Recipient\": \"" + recipient + "\",\n\"Message\": \"" + message + "\"\n}";
        System.out.println(json);
    }

    public static void startChat() {
        JOptionPane.showMessageDialog(null, "Welcome to Quickchat.");

        int numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
        String[][] allMessages = new String[numMessages][4];
        Random rand = new Random();
        int totalMessagesSent = 0;

        boolean running = true; // Outer menu loop
        while (running) {
            String option = JOptionPane.showInputDialog(
                    """
                            Select an option:
                            1) Send Messages
                            2) Show Recently Sent Messages (Coming Soon)
                            3) Quit"""
            );

            switch (option) {
                case "1" -> {
                    int messagesSent = 0; // Track messages sent for this session

                    while (messagesSent < numMessages) { // Inner message loop
                        String recipient = JOptionPane.showInputDialog("Enter recipient number (+CountryCode then number):");
                        JOptionPane.showMessageDialog(null, checkRecipientCell(recipient));

                        String message = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
                        JOptionPane.showMessageDialog(null, checkMessageLength(message));

                        String messageID = String.valueOf(1000000000 + rand.nextInt(900000000));
                        String hash = createMessageHash(messageID, messagesSent, message);

                        String sendChoice = JOptionPane.showInputDialog("Select:\n1) Send Message\n2) Disregard Message\n3) Store Message");
                        String status = sendMessage(sendChoice);
                        JOptionPane.showMessageDialog(null, status);

                        if (sendChoice.equals("1") || sendChoice.equals("3")) {
                            printMessages(messageID, hash, recipient, message);
                            storeMessage(allMessages, messagesSent, messageID, hash, recipient, message);
                        }

                        messagesSent++;
                        totalMessagesSent = returnTotalMessages(); // Keep total count updated
                    }
                }
                case "2" -> JOptionPane.showMessageDialog(null, "Coming Soon.");
                case "3" -> running = false; // Exit the menu loop
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent);
    }

}
