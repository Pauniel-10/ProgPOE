import javax.swing.JOptionPane;
import java.util.Random;

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
        if (cellNum.startsWith("+") && cellNum.length() <= 13)
            return "Cell phone number successfully captured.";
        else
            return "Cell phone number incorrectly formatted. Please correct the number.";
    }

    public static String createMessageHash(String messageID, int numMessage, String message) {
        String[] words = message.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return (messageID.substring(0, 2) + ":" + numMessage + ":" + firstWord + lastWord).toUpperCase();
    }

    public static String checkMessageLength(String message) {
        if (message.length() <= 250)
            return "Message ready to send.";
        else
            return "Message exceeds 250 characters by " + (message.length() - 250) + ", please reduce size.";
    }

    public static String sendMessage(String choice) {
        switch (choice) {
            case "1" -> {
                totalMessages++;
                return "Message successfully sent.";
            }
            case "2" -> {
                return "Press 0 to delete message.";
            }
            case "3" -> {
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid choice.";
            }
        }
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public static void printMessages(String messageID, String messageHash, String recipient, String message) {
        JOptionPane.showMessageDialog(null,
                "Message ID: " + messageID +
                        "\nMessage Hash: " + messageHash +
                        "\nRecipient: " + recipient +
                        "\nMessage: " + message);
    }

    // Placeholder for storing messages in JSON format
    public static void storeMessage(String[][] allMessages, int index, String messageID, String messageHash, String recipient, String message) {
        allMessages[index][0] = messageID;
        allMessages[index][1] = messageHash;
        allMessages[index][2] = recipient;
        allMessages[index][3] = message;

        String json = "{\n\"MessageID\": \"" + messageID + "\",\n\"MessageHash\": \"" + messageHash +
                "\",\n\"Recipient\": \"" + recipient + "\",\n\"Message\": \"" + message + "\"\n}";
        System.out.println(json); // Display JSON placeholder
    }

    public static void startChat() {
        JOptionPane.showMessageDialog(null, "Welcome to Quickchat.");
        Random rand = new Random();

        String numStr = JOptionPane.showInputDialog("How many messages would you like to send?");
        if (numStr == null) System.exit(0);
        int numMessages = Integer.parseInt(numStr);
        String[][] allMessages = new String[numMessages][4];

        int i = 0;
        label:
        while (i < numMessages) {
            String option = JOptionPane.showInputDialog(
                    "Options:\n1) Send Message\n2) Show Recently Sent Messages (Coming Soon)\n3) Quit"
            );
            if (option == null) System.exit(0);

            switch (option) {
                case "1":
                    String recipient = JOptionPane.showInputDialog("Enter recipient number (+CountryCode then number):");
                    if (recipient == null) System.exit(0);
                    JOptionPane.showMessageDialog(null, checkRecipientCell(recipient));

                    String message = JOptionPane.showInputDialog("Enter your message:");
                    if (message == null) System.exit(0);
                    JOptionPane.showMessageDialog(null, checkMessageLength(message));

                    String messageID = String.valueOf(1000000000 + rand.nextInt(900000000));
                    String hash = createMessageHash(messageID, i, message);

                    String sendChoice = JOptionPane.showInputDialog("Select:\n1) Send Message\n2) Disregard Message\n3) Store Message");
                    if (sendChoice == null) System.exit(0);

                    String status = sendMessage(sendChoice);
                    JOptionPane.showMessageDialog(null, status);

                    if (sendChoice.equals("1") || sendChoice.equals("3")) {
                        printMessages(messageID, hash, recipient, message);
                        storeMessage(allMessages, i, messageID, hash, recipient, message);
                    }

                    i++;
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;
                case "3":
                    break label;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
                    break;
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + returnTotalMessages());
    }

}
