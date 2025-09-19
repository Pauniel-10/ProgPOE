# ProgPOE
_______________________________________
This project is a simple Java-based login and registration system that allows users to:
•	Register with their first name, surname, username, password, and phone number.
•	Validate username, password, and phone number according to specific rules.
•	Log in with their registered credentials.
•	View a welcome message and user details upon successful login.
The project also includes JUnit tests to verify the correctness of the validation methods and login functionality.
________________________________________
Registration Rules
•	Username: Must contain an underscore _ and be no longer than 5 characters.
•	Password: Must be at least 8 characters long, contain one uppercase letter, one number, and one special character.
•	Phone Number: Must include an international country code (e.g., +27) and be no longer than 10 digits after the code.
Functionality
•	Register User: Prompts the user to enter details and validates them.
•	Login User: Allows the user to log in using username and password.
•	Validation Methods:
o	checkUserName() → Validates username.
o	checkPasswordComplexity() → Validates password.
o	checkCellPhoneNumber() → Validates phone number.
•	JUnit Tests: Ensures validation and login logic works correctly.
________________________________________
Project Structure
├── login.java        # Defines the Login class with validation and login methods
├── main.java         # Main program to run registration and login
├── logintest.java    # JUnit tests for validation and login functionality
________________________________________
How to Run
1.	Compile the files:
2.	javac login.java main.java
3.	Run the program:
4.	java org.example.Main
5.	Register a new user by following the prompts.
6.	Log in with the same credentials.
________________________________________
Running Tests
1.	Ensure you have JUnit in your classpath.
2.	Compile the test file:
3.	javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar logintest.java
4.	Run the tests:
5.	java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore loginTest
________________________________________
Example Run
====== Register here =======
Enter First Name: Kyle
Enter Surname: Walker
Enter Username: kyl_1
Enter Password: Password1!
Enter Phone number: +27831234567

Registration successful, please login

=== Login ===
Enter Username: kyl_1
Enter Password: Password1!
Welcome, Kyle Walker. It is great to see you again.

User Details:
 Name: Kyle Walker
 Username: kyl_1
 Phone Number: +27831234567

What would you like to do today?
________________________________________
Example Test Cases
•	✅ Valid Username: "kyl_1" → Passes
•	❌ Invalid Username: "kyle!!!!!!!" → Fails
•	✅ Valid Password: "Password1!" → Passes
•	❌ Invalid Password: "pass" → Fails
•	✅ Correct Login: "kyl_1", "Password1!" → Success
•	❌ Wrong Password: "kyl_1", "WrongPass" → Fail
________________________________________
Future Improvements
•	Encrypt passwords before storing.
•	Allow multiple users to register and save them in a file or database.
•	Improve error handling with custom exceptions.
•	Add a graphical user interface (GUI).
________________________________________
References
- OpenAI. (2025). *ChatGPT (Sep 17 version)* [Large language model]. Available at: https://chat.openai.com/ (Accessed: 17 September 2025).
