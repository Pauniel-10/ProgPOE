import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageTest {

    //Part 2 Message Tests
    //Part 2 Message Tests

    //Test that messages do not go over 250 characters
    @Test
    public void testMessageUnder250CharSuccess() {
        String msg = "Hello this is under 250 chars";
        String result = Message.checkMessageLength(msg);
        assertEquals("Message ready to send.", result);
        System.out.println(result);
    }
    // Test failure when messages go over 250 characters
    @Test
    public void testMessageOver250CharFailure() {
        String msg = "a".repeat(253);
        String result = Message.checkMessageLength(msg);
        assertEquals("Message exceeds 250 characters by 3, please reduce size.", result);
        System.out.println(result);
    }

    //Test that valid recipient phone number format passes validation
    @Test
    public void testCorrectNumberSuccess() {
        String num = "+27718693002";
        String result = Message.checkRecipientCellTest(num);
        assertEquals("Cell phone number successfully captured.", result);
        System.out.println(result);
    }

    //Test failure when recipient phone number is incorrect
    @Test
    public void testCorrectNumberFailure() {
        String num = "08575975889"; // missing '+'
        String result = Message.checkRecipientCellTest(num);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
        System.out.println(result);
    }

    //Test that Message ID is valid
    @Test
    public void testCheckMessageIDSuccess() {
        String testID = "123456789";
        String result = Message.generateMessageID(testID);
        assertEquals("Message ID generated: 123456789", result);
        System.out.println(result);
    }

    //Test failure when Message ID exceeds 10 characters
    @Test
    public void testCheckMessageIDFailure() {
        String testID = "12345678901";
        String result = Message.generateMessageID(testID);
        assertEquals("Invalid Message ID", result);
        System.out.println(result);
    }

    //Test that message hash is generated correctly for Task 1
    @Test
    public void testCreateMessageHashTask1() {
        String messageID = "00";
        int numMessage = 0;
        String message = "Hi Mike, can you join us for dinner tonight";
        String expected = "00:0:HITONIGHT";
        assertEquals(expected, Message.createMessageHash(messageID, numMessage, message));
    }

    //Test that message hash is generated correctly for Task 2
    @Test
    public void testCreateMessageHashTask2() {
        String messageID = "01";
        int numMessage = 1;
        String message = "Hello Keegan, did you receive the payment?";
        String expected = "01:1:HELLOPAYMENT?";
        assertEquals(expected, Message.createMessageHash(messageID, numMessage, message));
    }

    //Test that total messages counter is returned correctly
    @Test
    public void testReturnTotalMessages() {
        int before = Message.returnTotalMessages();
        assertEquals(before, Message.returnTotalMessages());
    }

    // Test send option
    @Test
    public void testSentMessageForTest_SendOption() {
        String result = Message.sentMessageTest("1");
        assertEquals("Message successfully sent.", result);
        System.out.println(result);
    }

    //Test Disregard option
    @Test
    public void testSentMessageForTest_DisregardOption() {
        String result = Message.sentMessageTest("2");
        assertEquals("Press 0 to delete message.", result);
        System.out.println(result);
    }

    //Test Store option
    @Test
    public void testSentMessageForTest_StoreOption() {
        String result = Message.sentMessageTest("3");
        assertEquals("Message successfully stored.", result);
        System.out.println(result);
    }
}
