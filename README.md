# ProgPOE

Repository: https://github.com/Pauniel-10/Prog_POE

Overview
--------
This project is the Programming POE (Practical of Exercise) for the Programming 1A module. It implements a small chat application with three parts:

- Part 1 — Registration and Login system (console-based)
- Part 2 — QuickChat messaging (dialog-driven after login; message entry via JOptionPane)
- Part 3 — Persistence, arrays and reporting (stored messages, arrays, search, delete, report)

The codebase is written in Java and includes JUnit 4 tests and a GitHub Actions CI workflow (Maven) to run automated tests.

Key design decisions
- Registration and login remain console-based (Scanner) so the user enters details in the terminal.
- After successful login, QuickChat opens and message entry/menus are presented with JOptionPane for the required UI behaviour.
- To support automated grading / headless CI runs, a headless mode is available so the program won't block on GUI dialogs in an automated environment (see Running section).
- Tests are implemented with JUnit 4 and the project includes a Maven pom.xml so the GitHub Actions workflow runs `mvn test`.

Part 1 — Login and Registration System
--------------------------------------
Features
- Register with: first name, last name, username, password and phone number.
- Validate inputs using the following rules:
  - Username: contains `_` and ≤ 5 characters.
  - Password: ≥ 8 characters, at least one uppercase letter, at least one number and at least one special character.
  - Phone number: starts with `+27` and contains digits after the prefix (max national part length enforced).
- Login using stored credentials (in-memory for the session).
- Welcome message printed on successful login.

Validation methods (in `Login.java`)
- `checkUserName()` — username format validation.
- `checkPasswordComplexity()` — password complexity validation.
- `checkCellPhoneNumber()` — South African phone format validation (starts with `+27`).

Part 2 — QuickChat Messaging System
----------------------------------
Overview
- After login, users interact with QuickChat to send, store or disregard messages.
- The menu and message entry use JOptionPane dialogs when not in headless mode to meet the rubric requirement.
- For each sent message:
  - A 10-digit Message ID is generated.
  - A Message Hash is created: first two chars of MessageID + ":" + message number + ":" + FIRSTWORD + LASTWORD (ALL CAPS).
  - The full message details are shown once in a JOptionPane in the exact order required:
    - Message ID
    - Message Hash
    - Recipient
    - Message
- After a batch of messages (the number the user chose to enter), a single summary dialog shows the accumulated total messages sent for that session.

Validation rules
- Message length: ≤ 250 characters.
- Recipient: must start with `+27` and contain only digits after the prefix.
- Message ID: ≤ 10 characters.

Storage
- Messages may be stored to `messages.json` as line-delimited JSON objects when the user chooses Store.
- Stored messages can be read back into the application (Part 3).

Part 3 — Arrays, Searching, Delete and Report
---------------------------------------------
Features
- The application maintains arrays (not lists) for:
  - Sent messages
  - Stored messages
  - Disregarded messages
  - Message hashes
  - Message IDs
- Users (and the test suite) can:
  - Display sender and recipient of all sent messages.
  - Display the longest sent message.
  - Search for a message by Message ID and display recipient + message.
  - Search for all messages belonging to a particular recipient.
  - Delete a message using its Message Hash (removes from arrays and `messages.json`).
  - Generate a Sent Messages report containing Message Hash, Recipient and Message.

Automated Unit Tests (CI)
-------------------------
- A Maven `pom.xml` is included at the repository root to compile and run tests with JUnit 4.
- GitHub Actions workflow file: `.github/workflows/test-java.yml` runs `mvn test` on push/PR to the configured branches.
- The workflow uploads Surefire reports as an artifact for debugging test failures.
- To satisfy the rubric item "Automated Unit Tests 4—5", ensure that:
  - Your `pom.xml` is present in the repository root.
  - The workflow file is present at `.github/workflows/test-java.yml`.
  - Tests are in `src/test/java` and compile & pass on the runner.

Project structure (Maven layout)
-------------------------------
```
Prog_POE/
├── pom.xml
├── .github/
│   └── workflows/
│       └── test-java.yml
├── messages.json          # persistent storage file (created at runtime)
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       ├── Main.java
    │       ├── Login.java
    │       ├── Message.java
    │       └── MessageService.java
    └── test/
        └── java/
            ├── LoginTest.java
            ├── MessageTest.java
            └── MessageServicePart3Test.java
```

Where to find files
- Main program: `src/main/java/Main.java`
- Login class: `src/main/java/Login.java`
- Message class: `src/main/java/Message.java`
- Message service (arrays and report): `src/main/java/MessageService.java`
- Tests: `src/test/java/*Test.java`
- CI workflow: `.github/workflows/test-java.yml`
- Maven build: `pom.xml`

How to compile & run (local)
---------------------------
Requirements:
- Java 17+
- Maven 3.6+

1) Build and run tests (recommended)
```bash
# from project root
mvn -B test
```

2) Compile and run the application
```bash
# compile classes
mvn -q compile

# run the main class (run from the classes folder)
java -cp target/classes Main
```

Headless mode (for automated graders / CI)
- The application normally uses JOptionPane after login for the QuickChat UI. For CI/automated graders you can start the application in headless mode (so no GUI dialogs block the run).
- To run in headless mode set an environment variable before running:
```bash
# Linux / macOS
export QUICKCHAT_HEADLESS=true
mvn -q test             # tests run without GUI interaction

# or to run the app in headless mode
export QUICKCHAT_HEADLESS=true
mvn -q compile
java -cp target/classes Main
```
- When headless is enabled the program uses console prompts rather than GUI dialogs for menus and send/store/disregard choices. This prevents CI from hanging on modal dialogs.

Running tests locally (IDE or command line)
- Using Maven (preferred):
```bash
mvn test
```
- Using IDE:
  - Import project as a Maven project.
  - Run the test classes: `LoginTest`, `MessageTest`, `MessageServicePart3Test`.

Notes about GUI & headless testing
- Unit tests are written to avoid GUI interactions (they use the non-GUI helpers). Running tests in CI should therefore not require headless overrides, but headless mode exists for running the application interactively on CI or headless machines.

JSON storage format
-------------------
Messages stored in `messages.json` are appended as one JSON object per line. Example:
```json
{
  "MessageID": "1283144317",
  "MessageHash": "12:0:HIDOING",
  "Sender": "+27831234567",
  "Recipient": "+27746440099",
  "Message": "Hi how are you doing"
}
```

Testing expectations (rubric excerpts)
- Part 2 (QuickChat):
  - Numeric menu displayed using `JOptionPane` and accepts 1–3 (manual grading).
  - Message details are displayed via `JOptionPane` AFTER the message has been sent and in the exact order:
    - Message ID, Message Hash, Recipient, Message
  - The total number of messages is shown once after the user has completed entering the set number of messages.
- Part 3:
  - Arrays are created and correctly populated with test data.
  - Longest message search, search by Message ID, search by recipient, delete by Message Hash and generate report are implemented and tested.

Known limitations and future work
--------------------------------
- Passwords are stored in plain text (in-memory). This is a security limitation for the current assignment and should be improved by hashing/encryption for production.
- The message storage file `messages.json` uses a simple line-delimited JSON format (one JSON object per line). For robust usage, consider using a proper JSON library (Jackson or Gson) and structured storage/cleanup.
- The application is intentionally simple to match the assignment scope; future improvements can include multi-user persistent storage, GUI improvements, and stronger error handling.

References
----------
- QuickChat architecture reference (learning material).
- GeeksforGeeks. File Handling in Java. https://www.geeksforgeeks.org/file-handling-in-java-using-filewriter-filereader/
- OpenAI. ChatGPT (GPT-4/5) [Large Language Model]. https://chat.openai.com
