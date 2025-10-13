import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {
    //Part 1 Login tests

    // Test that a valid username meets all the requirements
    @Test
    public void testValidUserName(){
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertTrue(user.checkUserName());

    }

    //Test that an invalid username fails validation
    @Test
    public void testInvalidUserName(){
        Login user = new Login("Kyle", "Walker", "kyle!!!!!!!", "Password1!", "+27831234567");
        assertFalse(user.checkUserName());
    }

    //Test that a valid password meets complexity rules
    @Test
    public void testValidPassword() {
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertTrue(user.checkPasswordComplexity());
    }
    //Test that a weak password fails complexity check
    @Test
    public void testInvalidPassword() {
        Login user = new Login("Kyle", "Walker", "kyl_1", "pass", "+27831234567");
        assertFalse(user.checkPasswordComplexity());
    }
    //Test login success when correct credentials are entered
    @Test
    public void testLoginSuccess(){
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertTrue(user.loginUser("kyl_1","Password1!"));
    }
    // Test login failure when password is incorrect
    @Test
    public void testLoginFailure(){
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertFalse(user.loginUser("kyl_1","WrongPass"));
    }

    //Phone number tests
    @Test
    public void testValidPhoneNumber() {
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567");
        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidPhoneNumberTooLong() {
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+2783123456789");
        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidPhoneNumberWrongPrefix() {
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+1234567890");
        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidPhoneNumberNonDigits() {
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27ABC123456");
        assertFalse(user.checkCellPhoneNumber());
    }


}
