import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {
    @Test
    public void testMessageUnder250CharSuccess() {
        String msg = "Hello this is under 250 chars";
        String result = Message.checkMessageLength(msg);
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageOver250CharFailure() {
        String msg = "a".repeat(253);
        String result = Message.checkMessageLength(msg);
        assertEquals("Message exceeds 250 characters by 3, please reduce size.", result);
    }

    @Test
    public void testCorrectNumberSuccess() {
        String num = "+27718693002";
        String result = Message.checkRecipientCellTest(num);
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testCorrectNumberFailure() {
        String num = "08575975889";
        String result = Message.checkRecipientCellTest(num);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testCheckMessageIDSuccess() {
        String testID = "123456789";
        String result = Message.generateMessageID(testID);
        assertEquals("Message ID generated: 123456789", result);
    }

    @Test
    public void testCheckMessageIDFailure() {
        String testID = "12345678901";
        String result = Message.generateMessageID(testID);
        assertEquals("Invalid Message ID", result);
    }

    @Test
    public void testCreateMessageHashTask1() {
        String messageID = "00";
        int numMessage = 0;
        String message = "Hi Mike, can you join us for dinner tonight";
        String expected = "00:0:HITONIGHT";
        assertEquals(expected, Message.createMessageHash(messageID, numMessage, message));
    }

    @Test
    public void testCreateMessageHashTask2() {
        String messageID = "01";
        int numMessage = 1;
        String message = "Hello Keegan, did you receive the payment?";
        String expected = "01:1:HELLOPAYMENT?";
        assertEquals(expected, Message.createMessageHash(messageID, numMessage, message));
    }
}