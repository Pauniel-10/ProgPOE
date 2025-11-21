import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;

public class Message {
    private final String messageID;
    private final String sender;
    private final String recipient;
    private final String message;
    private final String messageHash;

    public Message(String messageID, String sender, String recipient, String message, String messageHash) {
        this.messageID = messageID;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = messageHash;
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return message; }
    public String getMessageHash() { return messageHash; }
    public String getSender() { return sender; }

    // Message ID <= 10 characters
    public static boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    public static String generateMessageID(String messageID) {
        if (checkMessageID(messageID)) return "Message ID generated: " + messageID;
        return "Invalid Message ID";
    }

    // Recipient check: +<countrycode><national up to 10 digits>
    public static boolean checkRecipientCell(String cellNum) {
        if (cellNum == null) return false;
        return cellNum.matches("^\\+\\d{1,3}\\d{1,10}$");
    }

    // Test wrapper returning user-facing strings (used in JUnit)
    public static String checkRecipientCellTest(String cellNum) {
        if (checkRecipientCell(cellNum)) return "Cell phone number successfully captured.";
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Create message hash: first two chars of messageID, ':' , message index, ':' , firstWord + lastWord (caps)
    public static String createMessageHash(String messageID, int numMessage, String messageText) {
        String safeID = (messageID == null) ? "00" : (messageID.length() >= 2 ? messageID : String.format("%-2s", messageID).replace(' ', '0'));
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 0 ? words[words.length - 1].toUpperCase() : "";
        String hash = safeID.substring(0, 2) + ":" + numMessage + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // send/store/disregard, when QuickChat is open
    public static String sentMessageDialog() {
        while (true) {
            String menu = """
                    Select an option by entering the number:
                    1) Send Message
                    2) Disregard Message
                    3) Store Message to send later""";
            String choice = JOptionPane.showInputDialog(null, menu, "Send Message Options", JOptionPane.QUESTION_MESSAGE);
            if (choice == null) return "Disregard"; // treat cancel as disregard
            choice = choice.trim();
            switch (choice) {
                case "1": return "Send";
                case "2": return "Disregard";
                case "3": return "Store";
                default: JOptionPane.showMessageDialog(null, "Invalid choice. Please enter 1, 2 or 3.", "Invalid Choice", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // Print message details in the required order (MessageID, Message Hash, Recipient, Message)
    public void printMessages() {
        JOptionPane.showMessageDialog(null,
                "Message ID: " + messageID +
                        "\nMessage Hash: " + messageHash +
                        "\nRecipient: " + recipient +
                        "\nMessage: " + message
        );
    }

    // Store message as a single-line JSON object appended to messages.json
    public void storeMessage() {
        String json = "{"
                + "\"MessageID\":\"" + escape(messageID) + "\","
                + "\"Sender\":\"" + escape(sender) + "\","
                + "\"MessageHash\":\"" + escape(messageHash) + "\","
                + "\"Recipient\":\"" + escape(recipient) + "\","
                + "\"Message\":\"" + escape(message) + "\""
                + "}";
        try (FileWriter fw = new FileWriter("messages.json", true)) {
            fw.write(json + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // Read stored messages
    // Now returns Message[]
    public static Message[] readStoredMessages() {
        File f = new File("messages.json");
        if (!f.exists()) return new Message[0];
        ArrayList<Message> temp = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String id = extract(line, "MessageID");
                String sender = extract(line, "Sender");
                String hash = extract(line, "MessageHash");
                String recipient = extract(line, "Recipient");
                String msg = extract(line, "Message");
                if (id != null && recipient != null && msg != null) {
                    temp.add(new Message(id, sender == null ? "Unknown" : sender, recipient, msg, hash == null ? "" : hash));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading stored messages: " + e.getMessage());
        }
        return temp.toArray(new Message[0]);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String extract(String jsonLine, String key) {
        String pattern = "\"" + key + "\":\"";
        int idx = jsonLine.indexOf(pattern);
        if (idx == -1) return null;
        int start = idx + pattern.length();
        int end = jsonLine.indexOf("\"", start);
        if (end == -1) return null;
        return jsonLine.substring(start, end).replace("\\\"", "\"").replace("\\\\", "\\");
    }

    // Message length check used by tests
    public static String checkMessageLength(String msg) {
        if (msg == null) msg = "";
        if (msg.length() <= 250) return "Message ready to send.";
        int excess = msg.length() - 250;
        return "Message exceeds 250 characters by " + excess + ", please reduce size.";
    }
}