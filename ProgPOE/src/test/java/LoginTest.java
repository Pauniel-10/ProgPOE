import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;

public class LoginTest {

    // --------------------------
    // Original Login Tests
    // --------------------------
    @Test
    public void testValidUserName(){
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertTrue(user.checkUserName());
    }

    @Test
    public void testInvalidUserName(){
        Login user = new Login("Kyle", "Walker", "kyle!!!!!!!", "Password1!", "+27831234567");
        assertFalse(user.checkUserName());
    }

    @Test
    public void testValidPassword() {
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testInvalidPassword() {
        Login user = new Login("Kyle", "Walker", "kyl_1", "pass", "+27831234567");
        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testLoginSuccess(){
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertTrue(user.loginUser("kyl_1","Password1!"));
    }

    @Test
    public void testLoginFailure(){
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertFalse(user.loginUser("kyl_1","WrongPass"));
    }

    // --------------------------
    // Message Tests (Part 2)
    // --------------------------
    @Test
    public void testMessageLengthSuccess() {
        String msg = "Hello, this is a short test message!";
        assertEquals("Message ready to send.", Message.checkMessageLength(msg));
    }

    @Test
    public void testMessageLengthFailure() {
        String expected = "Message exceeds 250 characters by 10, please reduce size.";
        assertEquals(expected, Message.checkMessageLength("a".repeat(260) // 260 chars
        ));
    }

    @Test
    public void testRecipientValid() {
        String recipient = "+27831234567";
        assertEquals("Cell phone number successfully captured.", Message.checkRecipientCell(recipient));
    }

    @Test
    public void testRecipientInvalid() {
        String recipient = "0831234567"; // missing +
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                Message.checkRecipientCell(recipient));
    }

    @Test
    public void testMessageHash() {
        String messageID = "1234567890"; // simulated auto-generated
        int messageNum = 0;
        String msg = "Hi tonight"; // first word: Hi, last word: tonight
        String hash = Message.createMessageHash(messageID, messageNum, msg);
        assertEquals("12:0:HITONIGHT", hash);
    }

    @Test
    public void testMessageSendChoices() {
        // Reset totalMessages
        assertEquals(0, Message.returnTotalMessages());

        // Send
        assertEquals("Message successfully sent.", Message.sendMessage("1"));
        assertEquals(1, Message.returnTotalMessages());

        // Disregard
        assertEquals("Press 0 to delete message.", Message.sendMessage("2"));
        assertEquals(1, Message.returnTotalMessages()); // unchanged

        // Store
        assertEquals("Message successfully stored.", Message.sendMessage("3"));
        assertEquals(1, Message.returnTotalMessages()); // unchanged
    }

    @Test
    public void testMessageIDLength() {
        String messageID = "1234567890"; // 10 chars
        Message.createMessageHash(messageID, 0, "Hello world");
        Message msg = new Message(messageID);
        assertTrue(msg.checkMessageID());
    }

    // --------------------------
    // Test Data Simulation: Two Messages
    // --------------------------
    @Test
    public void testTwoMessagesFlow() {
        Random rand = new Random();

        // --- Message 1 ---
        String messageID1 = String.valueOf(1000000000 + rand.nextInt(900000000)); // auto-generated
        String msg1Content = "Hi Mike, can you join us for dinner tonight";
        String recipient1 = "+27718693002";
        Message.createMessageHash(messageID1, 1, msg1Content);

        // Check validations
        assertEquals("Cell phone number successfully captured.", Message.checkRecipientCell(recipient1));
        assertEquals("Message ready to send.", Message.checkMessageLength(msg1Content));
        assertEquals("Message successfully sent.", Message.sendMessage("1")); // Send

        // --- Message 2 ---
        String messageID2 = String.valueOf(1000000000 + rand.nextInt(900000000)); // auto-generated
        String msg2Content = "Hi Keegan, did you receive the payment?";
        String recipient2 = "+27857595889"; // standardized with international code
        Message.createMessageHash(messageID2, 2, msg2Content);

        // Check validations
        assertEquals("Cell phone number successfully captured.", Message.checkRecipientCell(recipient2));
        assertEquals("Message ready to send.", Message.checkMessageLength(msg2Content));
        assertEquals("Press 0 to delete message.", Message.sendMessage("2")); // Discard

        // Total messages should be 1 because only first was sent
        assertEquals(1, Message.returnTotalMessages());
    }
}
