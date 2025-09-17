import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class loginTest {
    @Test
    public void testValidUserName(){
        Login user = new Login("Kyle", "Walker", "kyl_1", "Password1!", "+27831234567" );
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

}