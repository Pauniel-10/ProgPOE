import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageTest {

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
}
