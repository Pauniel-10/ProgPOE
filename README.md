# ProgPOE
GitHub link:
https://github.com/Pauniel-10/Prog_POE

## Part 1 – Login and Registration System
This part of the project is a simple **Java-based login and registration system** that allows users to:

* Register with their first name, surname, username, password, and phone number.
* Validate username, password, and phone number according to specific rules.
* Log in with their registered credentials.
* View a welcome message and user details upon successful login.

The project also includes **JUnit tests** to verify the correctness of the validation methods and login functionality.

---

## Registration Rules

* **Username**: Must contain an underscore `_` and be no longer than **5 characters**.
* **Password**: Must be at least **8 characters long**, contain **one uppercase letter**, **one number**, and **one special character**.
* **Phone Number**: Must include an international country code (e.g., `+27`) and be no longer than **10 digits after the code**.

---

## Functionality

* **Register User**: Prompts the user to enter details and validates them.
* **Login User**: Allows the user to log in using username and password.
* **Validation Methods**:

  * `checkUserName()` → Validates username.
  * `checkPasswordComplexity()` → Validates password.
  * `checkCellPhoneNumber()` → Validates phone number.
* **JUnit Tests**: Ensures validation and login logic works correctly.

---

## Project Structure

```
├── login.java        # Defines the Login class with validation and login methods
├── main.java         # Main program to run registration and login
├── logintest.java    # JUnit tests for validation and login functionality
```

---

## Implementation Notes / Code Explanation

This login system is implemented in **three main files**: `login.java`, `main.java`, and `logintest.java`.

**1. `login.java` – Login Class**

* **Purpose**: Handles user registration, validation, and login functionality.
* **Attributes**:

  * `FName`, `SName` → Stores the user’s first and last names.
  * `UserName`, `Password` → Stores the credentials for login.
  * `PhoneNum` → Stores the user’s phone number with international code.
* **Key Methods**:

  * `checkUserName()` → Ensures the username contains an underscore `_` and is no longer than 5 characters.
  * `checkPasswordComplexity()` → Validates the password is at least 8 characters, contains an uppercase letter, a number, and a special character.
  * `checkCellPhoneNumber()` → Validates the phone number format, including the international code.
  * `registerUser()` → Static method that prompts the user for registration details, validates input, and returns a `Login` object if all data is correct.
  * `loginUser()` → Checks if the entered username and password match the stored credentials.
  * `returnLoginStatus()` → Prints a welcome message and user details if login is successful, otherwise shows an error.

**2. `main.java` – Main Program**

* Calls `registerUser()` to allow a new user to register.
* If registration succeeds, prompts the user to log in.
* Displays login results and user details using `returnLoginStatus()`.

**3. `logintest.java` – JUnit Tests**

* Contains tests to ensure each method behaves correctly.
* **Tests include**:

  * Valid and invalid username checks.
  * Valid and invalid password checks.
  * Successful and failed login attempts.

**Additional Notes**

* The phone number regex used in `checkCellPhoneNumber()` was suggested by **ChatGPT (OpenAI, 2025)**.
* The system is **console-based**; user data is stored only during runtime and is **not persisted** after the program ends.
* Passwords are stored in **plain text**, which is a security limitation to be improved in future versions.

---
## Where to find the files in the folder

Open the ProgPOE folder, then navigate to the folder named "src". You'll find the main.java and the login.java files in the main/java folder. You'll find the loginTest.java file in the test/java folder.

---
## How to Run

```bash
# Compile the files
javac login.java main.java

# Run the program
java org.example.Main
```

* Register a new user by following the prompts.
* Log in with the same credentials.

---

## Running Tests

```bash
# Compile the test file (ensure JUnit is in classpath)
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar logintest.java

# Run the tests
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore loginTest
```

---

## Example Run

```
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
```

---

## Example Test Cases

* ✅ Valid Username: `"kyl_1"` → Passes
* ❌ Invalid Username: `"kyle!!!!!!!"` → Fails
* ✅ Valid Password: `"Password1!"` → Passes
* ❌ Invalid Password: `"pass"` → Fails
* ✅ Correct Login: `"kyl_1", "Password1!"` → Success
* ❌ Wrong Password: `"kyl_1", "WrongPass"` → Fail

---

## Limitations & Known Issues

* Passwords are stored in **plain text**.
* User data is **not persisted** after program termination.

---

## Future Improvements

* Encrypt passwords before storing.
* Allow multiple users to register and save them in a **file or database**.
* Improve error handling with **custom exceptions**.
* Add a **graphical user interface (GUI)**.

---

## Part 2 – QuickChat Messaging System
## **Overview**

QuickChat is a console-based Java messaging application that allows users to:

* Register and login with secure credentials.
* Send messages to recipients.
* Store messages in JSON format.
* Validate usernames, passwords, phone numbers, message length, and message IDs.
* Track the total number of messages sent.

This update reflects the latest version of the program, including improved phone number validation, message storage, and message handling workflows.

---

## **File Structure**

| File               | Description                                                                                                                              |
| ------------------ | ---------------------------------------------------------------------------------------------------------------------------------------- |
| `Main.java`        | Main entry point of the program. Handles registration, login, and the main menu for sending, storing, and managing messages.             |
| `Login.java`       | Handles user registration, validation, and login logic. Validates username, password, and phone number format.                           |
| `Message.java`     | Handles message creation, message ID/hash generation, recipient validation, storage to JSON, and message length checks.                  |
| `LoginTest.java`   | JUnit tests for login-related functionality (username, password, phone number validation, login success/failure).                        |
| `MessageTest.java` | JUnit tests for message-related functionality (message length, recipient validation, message ID/hash, and send/store/disregard options). |

---

## **Features**

### **Registration**

* **Username**: Must contain `_` and be no longer than 5 characters.
* **Password**: Must be at least 8 characters and include:

  * At least one capital letter.
  * At least one number.
  * At least one special character.
* **Phone Number**: Must start with `+27` and be no longer than 12 characters, containing only digits.

### **Login**

* Users log in using their registered username and password.
* On successful login, a personalized welcome message is displayed with user details.

### **Messaging**

* **Send Messages**: Users can specify how many messages to send in a session.
* **Message Validation**:

  * Maximum 250 characters.
  * Recipient number must start with `+27` and contain up to 10 digits.
  * Message IDs must be ≤ 10 characters.
* **Message Hash**: Generated using the message ID, message number, and first/last words of the message.
* **Store Messages**: Messages can be stored in `messages.json` for later retrieval.
* **Display Messages**: Sent or stored messages are displayed in a `JOptionPane` popup.

---

## **JUnit Testing**

### **Login Tests (`LoginTest.java`)**

* **Username Validation**:

  * Valid username passes.
  * Invalid username fails.
* **Password Validation**:

  * Valid password passes.
  * Weak password fails.
* **Login Validation**:

  * Correct credentials pass.
  * Incorrect credentials fail.
* **Phone Number Validation**:

  * Valid `+27` number passes.
  * Too long, wrong prefix, or non-digit numbers fail.

### **Message Tests (`MessageTest.java`)**

* **Message Length Validation**:

  * Under 250 characters passes.
  * Over 250 characters fails.
* **Recipient Number Validation**:

  * Correct `+27` format passes.
  * Incorrect formats fail.
* **Message ID Validation**:

  * Valid ID passes.
  * ID over 10 characters fails.
* **Message Hash Generation**:

  * Hashes correctly generated for given input.
* **Send/Disregard/Store Options**:

  * Each option returns the correct response.

---

## **How to Run**

### **Requirements**

* Java 17+ installed.
* IDE (IntelliJ, Eclipse) or command-line compiler.
* JUnit 4 for testing.

### **Running the Application**

1. Compile all Java files.
2. Run `Main.java`.
3. Follow the prompts for registration, login, and message handling.

### **Running Tests**

1. Ensure JUnit 4 is added to the project.
2. Run `LoginTest.java` and `MessageTest.java` in your IDE or using Maven:

```bash
mvn test
```

---

## **JSON Storage**

* Stored messages are appended to `messages.json`.
* JSON is generated only when `storeMessage()` is called.
* Example:

```json
{
  "MessageID": "1283144317",
  "MessageHash": "12:0:HIDOING",
  "Recipient": "+27746440099",
  "Message": "Hi how are you doing"
}
```

---

## **Notes**

* Total message counter updates for every sent or stored message.
* Pop-ups (`JOptionPane`) show details after sending or storing a message.
* The application continues until the user chooses "Quit".
* Phone numbers are strictly validated to start with `+27` and contain only digits after the prefix.

---

## **References**
* GeeksforGeeks. File Handling in Java. https://www.geeksforgeeks.org/file-handling-in-java-using-filewriter-filereader/ [Accessed 11 Oct. 2025].
* OpenAI. ChatGPT (GPT-4/5) [Large Language Model]. https://chat.openai.com [Accessed 11 Oct. 2025].
 
