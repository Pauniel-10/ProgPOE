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

Perfect 👍 here’s **Part 2** written to **match your Part 1 style exactly** — same structure, tone, and formatting.
You can paste this directly **below Part 1** in your README file.

---

## Part 2 – QuickChat Messaging System

This part of the project expands on the login and registration system by introducing **QuickChat**, a simple **Java-based messaging system** that allows users to send, store, or disregard messages after logging in.

QuickChat simulates a basic chat interface within the console and continues running until the user chooses to quit.

---

## Message Rules

* **Recipient Number**:
  Must begin with `+27`, contain only digits after the prefix, and be no longer than **12 characters** in total.
  *(e.g., `+27731234567` is valid)*

* **Message Length**:
  Each message must be **250 characters or fewer**.

* **Message ID and Hash**:
  Each message is automatically assigned a unique ID and a hash value that combines the ID, message number, and first and last words of the message.

---

## Functionality

* **Send Message**:
  Allows the user to enter a recipient number and message, validates both, and sends the message.

* **Store Message**:
  Saves the message for later sending.

* **Disregard Message**:
  Cancels the current message.

* **Message Summary**:
  After each message, details are displayed in both a **JOptionPane pop-up** and as formatted JSON in the console.

* **Session Control**:
  The program continues running until the user selects the “Quit” option.

---

## Project Structure

```
├── Login.java         # Handles registration and login
├── Message.java       # Handles message creation, validation, and sending
├── Main.java          # Controls program flow (login + QuickChat menu)
├── LoginTest.java     # Tests for login functionality
├── MessageTest.java   #Tests for message functionality
```

---

## Implementation Notes / Code Explanation

QuickChat is implemented in **message.java** and integrated through the **main.java** class.

**1. ` Message.java` – Message Class**

* **Purpose**: Handles all logic related to creating, validating, and managing messages.

* **Attributes**:

  * `messageID` – A unique ID for each message.
  * `recipient` – The receiver’s phone number (validated).
  * `message` – The message content.
  * `messageHash` – A generated code based on the message details.

* **Key Methods**:

  * `checkMessageID()` → Ensures the message ID does not exceed 10 characters.
  * `checkRecipientCell()` → Validates that the number starts with `+27` and contains only digits (max length 12).
  * `checkMessageLength()` → Confirms message is ≤ 250 characters.
  * `createMessageHash()` → Builds a hash from message ID, count, and first/last words.
  * `sentMessage()` → Asks the user to choose Send / Store / Disregard.
  * `printMessages()` → Displays the message details in a pop-up window.
  * `storeMessage()` → Prints message information in JSON format to the console.
  * `startChat()` → Displays the QuickChat menu and manages the user interaction loop.

**2. `Main.java` – Main Program**

* Launches after successful login.
* Displays the QuickChat main menu with the following options:

  1. Send Messages
  2. Show Recently Sent Messages
  3. Quit
* Continues running until the user selects **Quit**, allowing multiple messages per session.

---

## Example Run

```
How many messages would you like to send? 
1
Select an option:
1) Send Messages
2) Show recently sent messages
3) Quit
Enter choice: 1

Enter recipient number (+CountryCode and number): +27746440099
Enter your message (max 250 chars): Hi how are you doing
Message ready to send
Select:
1) Send Message
2) Disregard Message
3) Store Message to send later
Enter choice: 1
{
"MessageID": "1283144317",
"MessageHash": "12:0:HIDOING",
"Recipient": "+27746440099",
"Message": "Hi how are you doing"
}
Select an option:
1) Send Messages
2) Show recently sent messages
3) Quit
Enter choice: 3
Total messages sent: 1
```

---

## Example Test Cases

* ✅ Valid Recipient: `+27741234567` → Passes
* ❌ Invalid Recipient: `0712345678` → Fails
* ✅ Valid Message: `"Hope you’re well!"` → Passes
* ❌ Invalid Message: Message over 250 characters → Fails
* ✅ Valid Option: `1 → Send Message`
* ✅ Loop continues until user selects `3 → Quit`

---

## Where to Find the Files in the Folder

Open the **ProgPOE** folder, then navigate to **src**.
Inside **main/java**, you’ll find the following files:

* `Main.java`
* `Login.java`
* `Message.java`

In **test/java**, you’ll find:

* `loginTest.java`
* `MessageTest.java`

---

## How to Run

```bash
# Compile the files
javac Login.java Message.java Main.java
```

```bash
# Run the program
java org.example.Main
```

* Register and log in using the prompts.
* After successful login, the **QuickChat** menu will appear.
* Follow the prompts to send, store, or disregard messages.
* Continue chatting until you choose to quit.

---

## Example Message Output

When a message is sent or stored, details appear in the console as formatted JSON:

```
{
"MessageID": "1785642314",
"MessageHash": "17:1:HELLOFRIEND",
"Recipient": "+27731234567",
"Message": "Hello friend"
}
```

---

## Limitations & Known Issues

* Stored messages are **not saved permanently** (data clears when program ends).
* The “Show Recently Sent Messages” option is **not yet implemented**.
* Message IDs are randomly generated but not guaranteed unique across sessions.

---

## Future Improvements

* Save sent messages to a text file or database.
* Display message history under “Show Recently Sent Messages”.
* Add JUnit tests for message validation and flow.
* Introduce a simple **GUI chat window** for better user experience.
* Allow multiple users and message threads.

---

## References

* OpenAI. (2025). *ChatGPT (Sep 17 version)* [Large language model]. Available at: [https://chat.openai.com/](https://chat.openai.com/) (Accessed: 17 September 2025).

---

