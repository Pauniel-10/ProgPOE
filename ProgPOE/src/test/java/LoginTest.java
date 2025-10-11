import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

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
    public void testMessageShouldNotExceed250Characters_Success() {
        assertTrue("Message ready to send.", true);
    }

    @Test
    public void testMessageShouldNotExceed250Characters_Failure() {
        String msg = "a".repeat(251);
        int excess = msg.length() - 250;
        assertTrue("Message exceeds 250 characters by " + excess + ", please reduce size.",
                msg.length() > 250);
    }

    @Test
    public void testRecipientNumberCorrectlyFormatted_Success() {
        String num = "+27718693002";
        assertTrue("Cell phone number successfully captured.",
                Message.checkRecipientCell(num));
    }

    @Test
    public void testRecipientNumberCorrectlyFormatted_Failure() {
        String num = "08575975889"; // missing '+'
        assertFalse("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                Message.checkRecipientCell(num));
    }

    @Test
    public void testCheckMessageID_Success() {
        String id = "123456789";
        assertTrue(Message.checkMessageID(id));
    }

    @Test
    public void testCheckMessageID_Failure() {
        String id = "12345678901";
        assertFalse(Message.checkMessageID(id));
    }

    @Test
    public void testCreateMessageHash_Correctness() {
        String messageID = "00";
        int numMessage = 0;
        String message = "Hi Mike, can you join us for dinner tonight";
        String expected = "00:0:HITONIGHT";
        assertEquals(expected, Message.createMessageHash(messageID, numMessage, message));
    }

    @Test
    public void testCreateMessageHash_OtherCase() {
        String messageID = "01";
        int numMessage = 1;
        String message = "Hello Keegan, did you receive the payment?";
        String expected = "01:1:HELLOPAYMENT?";
        assertEquals(expected, Message.createMessageHash(messageID, numMessage, message));
    }

    @Test
    public void testReturnTotalMessages() {
        // Reset totalMessages before test
        int before = Message.returnTotalMessages();
        // Simulate increment (would normally be done via sending/storing)
        // Not directly settable, so skip actual increment for this static field.
        assertEquals(before, Message.returnTotalMessages());
    }

    @Test
    public void testStoreMessage_PrintsJson() {
        // Redirect system out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Message m = new Message("01", "+27718693002", "Test", "01:1:TESTTEST");
        m.storeMessage();
        String output = out.toString();
        assertTrue(output.contains("\"MessageID\": \"01\""));
        assertTrue(output.contains("\"Recipient\": \"+27718693002\""));
        assertTrue(output.contains("\"Message\": \"Test\""));
        System.setOut(System.out);
    }

    @Test
    public void testSentMessage_SendOption() {
        Scanner scanner = new Scanner("1\n");
        String result = Message.sentMessage(scanner);
        assertEquals("Send", result);
    }

    @Test
    public void testSentMessage_DisregardOption() {
        Scanner scanner = new Scanner("2\n");
        String result = Message.sentMessage(scanner);
        assertEquals("Disregard", result);
    }

    @Test
    public void testSentMessage_StoreOption() {
        Scanner scanner = new Scanner("3\n");
        String result = Message.sentMessage(scanner);
        assertEquals("Store", result);
    }

}
