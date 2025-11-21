import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageServiceTest {

    private MessageService svc;

    @Before
    public void setUp() {
        svc = new MessageService();

        // Test Data Message 1 (Sent)
        Message m1 = new Message("1000000001", "+27831234567", "+27834557896", "Did you get the cake?", "10:0:DIDCAKE");
        svc.addMessage(m1, "Sent");

        // Test Data Message 2 (Stored)
        Message m2 = new Message("1000000002", "+27831234567", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "10:1:WHEREON");
        svc.addMessage(m2, "Stored");

        // Test Data Message 3 (Disregard)
        Message m3 = new Message("1000000003", "+27831234567", "+27834484567", "Yohoooo, I am at your gate.", "10:2:YOHOGATE");
        svc.addMessage(m3, "Disregard");

        // Test Data Message 4 (Sent) - developer number
        Message m4 = new Message("1000000004", "0838884567", "0838884567", "It is dinner time !", "10:3:ITTIME!");
        svc.addMessage(m4, "Sent");

        // Test Data Message 5 (Stored)
        Message m5 = new Message("1000000005", "+27831234567", "+27838884567", "Ok, I am leaving without you.", "10:4:OKYOU");
        svc.addMessage(m5, "Stored");
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        Message[] sentArray = svc.getSentMessages();
        String[] actualTexts = new String[sentArray.length];
        for (int i = 0; i < sentArray.length; i++) actualTexts[i] = sentArray[i].getMessageText();

        String[] expected = new String[] { "Did you get the cake?", "It is dinner time !" };
        assertArrayEquals("Sent messages texts should match expected", expected, actualTexts);
    }

    @Test
    public void testDisplayLongestMessageAcrossAllMessages() {
        Message[] sent = svc.getSentMessages();
        Message[] stored = svc.getStoredMessages();
        Message[] disregarded = svc.getDisregardedMessages();

        String longest = null;
        for (Message message : sent) {
            String t = message.getMessageText();
            if (longest == null || t.length() > longest.length()) longest = t;
        }
        for (Message message : stored) {
            String t = message.getMessageText();
            if (longest == null || t.length() > longest.length()) longest = t;
        }
        for (Message message : disregarded) {
            String t = message.getMessageText();
            if (longest == null || t.length() > longest.length()) longest = t;
        }

        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals("Longest message across all arrays should match expected", expected, longest);
    }

    @Test
    public void testSearchForMessageID_returnsRecipientAndMessage() {
        Message found = svc.findByMessageID("1000000004");
        assertNotNull("Message with ID 1000000004 should be found", found);
        assertEquals("Recipient for message 4 should be 0838884567", "0838884567", found.getRecipient());
        assertEquals("Message text for message 4 should match", "It is dinner time !", found.getMessageText());
    }

    @Test
    public void testSearchAllMessagesForRecipient_returnsBothMessages() {
        String[] results = svc.findMessagesByRecipient("+27838884567");

        boolean foundFirst = false;
        boolean foundSecond = false;
        for (String result : results) {
            if ("Where are you? You are late! I have asked you to be on time.".equals(result)) foundFirst = true;
            if ("Ok, I am leaving without you.".equals(result)) foundSecond = true;
        }

        assertTrue("Should contain the stored long message", foundFirst);
        assertTrue("Should contain the stored 'Ok, I am leaving without you.' message", foundSecond);
        assertEquals("Should return exactly 2 messages for this recipient", 2, results.length);
    }

    @Test
    public void testDeleteMessageUsingMessageHash_deletesAndCannotBeFound() {
        boolean deleted = svc.deleteByHash("10:1:WHEREON");
        assertTrue("deleteByHash should return true when deletion occurs", deleted);

        Message shouldBeNull = svc.findByMessageID("1000000002");
        assertNull("Message with ID 1000000002 should be null after deletion", shouldBeNull);

        String[] remainingForRecipient = svc.findMessagesByRecipient("+27838884567");
        for (String s : remainingForRecipient) {
            assertNotEquals("Deleted message should not be present", "Where are you? You are late! I have asked you to be on time.", s);
        }
        boolean otherExists = false;
        for (String s : remainingForRecipient)
            if ("Ok, I am leaving without you.".equals(s)) {
                otherExists = true;
                break;
            }
        assertTrue("Other stored message for recipient should remain", otherExists);
    }

    @Test
    public void testDisplayReport_includesMessageHashRecipientAndMessage_forSentMessages() {
        String report = svc.generateReport();
        assertTrue("Report should contain hash for message 1", report.contains("Message Hash: 10:0:DIDCAKE"));
        assertTrue("Report should contain recipient for message 1", report.contains("Recipient: +27834557896"));
        assertTrue("Report should contain message text for message 1", report.contains("Did you get the cake?"));

        assertTrue("Report should contain hash for message 4", report.contains("Message Hash: 10:3:ITTIME!"));
        assertTrue("Report should contain recipient for message 4", report.contains("Recipient: 0838884567"));
        assertTrue("Report should contain message text for message 4", report.contains("It is dinner time !"));
    }
}