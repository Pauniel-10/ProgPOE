import java.io.*;

public class MessageService {
    private Message[] sentMessages = new Message[0];
    private Message[] storedMessages = new Message[0];
    private Message[] disregardedMessages = new Message[0];
    private String[] messageHashes = new String[0];
    private String[] messageIDs = new String[0];

    // Add a message to the appropriate array and update id/hash arrays
    public void addMessage(Message m, String flag) {
        if (m == null) return;
        messageHashes = appendString(messageHashes, m.getMessageHash());
        messageIDs = appendString(messageIDs, m.getMessageID());
        switch (flag) {
            case "Sent" -> sentMessages = appendMessage(sentMessages, m);
            case "Stored" -> storedMessages = appendMessage(storedMessages, m);
            case "Disregard" -> disregardedMessages = appendMessage(disregardedMessages, m);
            default -> System.out.println("Unknown flag: " + flag);
        }
    }

    // Getters returning arrays
    public Message[] getSentMessages() { return sentMessages; }
    public Message[] getStoredMessages() { return storedMessages; }
    public Message[] getDisregardedMessages() { return disregardedMessages; }

    // Return an array of "Sender: X, Recipient: Y" for sent messages and also print to console
    public String[] displaySenderRecipientOfSent() {
        String[] results = new String[sentMessages.length];
        for (int i = 0; i < sentMessages.length; i++) {
            Message m = sentMessages[i];
            String line = "Sender: " + m.getSender() + ", Recipient: " + m.getRecipient();
            results[i] = line;
            System.out.println(line);
        }
        return results;
    }

    // Find the longest sent message text (returns null if none)
    public String getLongestSentMessage() {
        String longest = null;
        for (Message m : sentMessages) {
            if (longest == null || m.getMessageText().length() > longest.length()) {
                longest = m.getMessageText();
            }
        }
        return longest;
    }

    // Find message by ID across all arrays
    public Message findByMessageID(String id) {
        for (Message m : sentMessages) if (m.getMessageID().equals(id)) return m;
        for (Message m : storedMessages) if (m.getMessageID().equals(id)) return m;
        for (Message m : disregardedMessages) if (m.getMessageID().equals(id)) return m;
        return null;
    }

    // Return array of message texts for a particular recipient across sent and stored lists
    public String[] findMessagesByRecipient(String recipient) {
        // count matches first
        int count = 0;
        for (Message m : sentMessages) if (m.getRecipient().equals(recipient)) count++;
        for (Message m : storedMessages) if (m.getRecipient().equals(recipient)) count++;
        String[] results = new String[count];
        int idx = 0;
        for (Message m : sentMessages) if (m.getRecipient().equals(recipient)) results[idx++] = m.getMessageText();
        for (Message m : storedMessages) if (m.getRecipient().equals(recipient)) results[idx++] = m.getMessageText();
        return results;
    }

    // Delete message(s) by hash across all collections; returns true if any deletion occurred
    public boolean deleteByHash(String hash) {
        boolean deleted = false;

        Message[] newSent = removeMessagesWithHash(sentMessages, hash);
        if (newSent.length != sentMessages.length) deleted = true;
        sentMessages = newSent;

        Message[] newStored = removeMessagesWithHash(storedMessages, hash);
        if (newStored.length != storedMessages.length) deleted = true;
        storedMessages = newStored;

        Message[] newDisregarded = removeMessagesWithHash(disregardedMessages, hash);
        if (newDisregarded.length != disregardedMessages.length) deleted = true;
        disregardedMessages = newDisregarded;

        if (deleted) {
            messageHashes = removeString(messageHashes, hash);
            // Also remove from persistent storage so file and arrays stay consistent
            removeHashFromJsonFile(hash);
        }
        return deleted;
    }

    // Rewrite messages.json excluding any lines that contain the exact message hash value
    private void removeHashFromJsonFile(String hash) {
        File file = new File("messages.json");
        if (!file.exists()) return;
        File temp = new File("messages.tmp");
        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"MessageHash\":\"" + hash + "\"")) {
                    // skip this line (delete)
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error removing hash from file: " + e.getMessage());
            return;
        }
        if (!file.delete()) {
            System.out.println("Warning: could not delete original messages.json");
            return;
        }
        if (!temp.renameTo(file)) {
            System.out.println("Warning: could not rename temp file to messages.json");
        }
    }

    // Generate report string for sent messages (hash, recipient, message)
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Sent Messages Report:\n");
        for (Message m : sentMessages) {
            sb.append("Message Hash: ").append(m.getMessageHash())
                    .append(" | Recipient: ").append(m.getRecipient())
                    .append(" | Message: ").append(m.getMessageText()).append("\n");
        }
        String report = sb.toString();
        System.out.println(report);
        return report;
    }

    // Load stored messages from messages.json into storedMessages array
    public void loadStoredMessagesFromFile() {
        Message[] fromFile = Message.readStoredMessages();
        for (Message m : fromFile) addMessage(m, "Stored");
    }

    private Message[] appendMessage(Message[] arr, Message m) {
        Message[] n = new Message[arr.length + 1];
        System.arraycopy(arr, 0, n, 0, arr.length);
        n[arr.length] = m;
        return n;
    }

    private Message[] removeMessagesWithHash(Message[] arr, String hash) {
        // Count items that do NOT match the hash
        int keep = 0;
        for (Message m : arr) {
            if (!m.getMessageHash().equals(hash)) keep++;
        }
        Message[] result = new Message[keep];
        int idx = 0;
        for (Message m : arr) {
            if (!m.getMessageHash().equals(hash)) {
                result[idx++] = m;
            }
        }
        return result;
    }

    private String[] appendString(String[] arr, String s) {
        String[] n = new String[arr.length + 1];
        System.arraycopy(arr, 0, n, 0, arr.length);
        n[arr.length] = s;
        return n;
    }

    private String[] removeString(String[] arr, String target) {
        int keep = 0;
        for (String s : arr) if (!s.equals(target)) keep++;
        String[] res = new String[keep];
        int idx = 0;
        for (String s : arr) if (!s.equals(target)) res[idx++] = s;
        return res;
    }
}