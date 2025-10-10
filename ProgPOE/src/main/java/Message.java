import javax.swing.JOptionPane;
import java.util.Random;
import javax.swing.JTextArea;

public class Message {

    private String messageID;
    private String messageHash;
    private String recipient;
    private String message;
    private static int totalMessages = 0;

    public Message(String messageID, String messageHash, String recipient, String message) {
        this.messageID = messageID;
        this.messageHash = messageHash;
        this.recipient = recipient;
        this.message = message;
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

        String inputNum = JOptionPane.showInputDialog("How many messages would you like to send?");
        if (inputNum == null) return;
        int numMessages = Integer.parseInt(inputNum);
        String[][] allMessages = new String[numMessages][4];
        Random rand = new Random();

        int sentMessages = 0;
        while (sentMessages < numMessages) {
            String option = JOptionPane.showInputDialog(
                    "Options:\n1) Send Message\n2) Show Recently Sent Messages (Coming Soon)\n3) Quit");
            if (option == null) continue;

            if (option.equals("1")) {
                String recipient = JOptionPane.showInputDialog("Enter recipient number (+CountryCode then number):");
                if (recipient == null) continue;
                JOptionPane.showMessageDialog(null, checkRecipientCell(recipient));

                String message = JOptionPane.showInputDialog("Enter your message:");
                if (message == null) continue;
                JOptionPane.showMessageDialog(null, checkMessageLength(message));

                String messageID = String.valueOf(1000000000 + rand.nextInt(900000000));
                String hash = createMessageHash(messageID, sentMessages, message);

                String sendChoice = JOptionPane.showInputDialog(
                        "Select:\n1) Send Message\n2) Disregard Message\n3) Store Message");
                if (sendChoice == null) continue;

                String status = sendMessage(sendChoice);
                JOptionPane.showMessageDialog(null, status);

                if (sendChoice.equals("1") || sendChoice.equals("2") || sendChoice.equals("3")) {
                    printMessages(messageID, hash, recipient, message);
                    storeMessage(allMessages, sentMessages, messageID, hash, recipient, message);
                    sentMessages++;
                }

            } else if (option.equals("2")) {
                JOptionPane.showMessageDialog(null, "Coming Soon.");
            } else if (option.equals("3")) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + returnTotalMessages());
    }
}
