# ProgPOE

This project is a simple **Java-based login and registration system** that allows users to:

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

## Implementation Notes

The phone number validation method (`checkCellPhoneNumber`) uses a regex pattern suggested by **ChatGPT** (OpenAI, 2025).

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

* The phone number validation in `main.java` currently uses a **hardcoded number** instead of the user's input.
* Passwords are stored in **plain text**.
* User data is **not persisted** after program termination.

---

## Future Improvements

* Encrypt passwords before storing.
* Allow multiple users to register and save them in a **file or database**.
* Improve error handling with **custom exceptions**.
* Add a **graphical user interface (GUI)**.

---

## References

* OpenAI. (2025). *ChatGPT (Sep 17 version)* \[Large language model]. Available at: [https://chat.openai.com/](https://chat.openai.com/) (Accessed: 17 September 2025).
* Oracle. (2025). *Java Platform, Standard Edition Documentation*. Available from [https://docs.oracle.com/javase/](https://docs.oracle.com/javase/)
* JUnit. (2025). *JUnit 4 Documentation*. Available from [https://junit.org/junit4/](https://junit.org/junit4/)

---


