import java.util.Scanner;

public class message {

    public static void chat() {
        Scanner reader = new Scanner(System.in);
        String[] messages = new String[5]; // fixed-size array
        int count = 0;
        boolean running = true;

        System.out.println("\n=== Welcome to QuickChat ===");

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Send a message");
            System.out.println("2. View messages");
            System.out.println("3. Delete a message");
            System.out.println("4. Exit");
            System.out.print("Choose an option (Enter only the number): ");
            int option = reader.nextInt();
            reader.nextLine(); // consume leftover newline

            if (option == 1) {
                if (count < messages.length) {
                    System.out.print("Enter your message: ");
                    String message = reader.nextLine();
                    messages[count] = message;
                    count++;
                    System.out.println("Message saved.");
                } else {
                    System.out.println("Message list full! Please delete one first.");
                }

            } else if (option == 2) {
                System.out.println("\nYour Messages:");
                if (count == 0) {
                    System.out.println("No messages yet.");
                } else {
                    for (int i = 0; i < count; i++) {
                        System.out.println((i + 1) + ". " + messages[i]);
                    }
                }

            } else if (option == 3) {
                System.out.print("Enter message number to delete: ");
                int msgNum = reader.nextInt();
                reader.nextLine();
                if (msgNum > 0 && msgNum <= count) {
                    for (int i = msgNum - 1; i < count - 1; i++) {
                        messages[i] = messages[i + 1];
                    }
                    messages[count - 1] = null;
                    count--;
                    System.out.println("Message deleted.");
                } else {
                    System.out.println("Invalid message number.");
                }

            } else if (option == 4) {
                running = false;
                System.out.println("Exiting...");

            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }
}
