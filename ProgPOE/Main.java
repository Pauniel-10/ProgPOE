package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        //Call static method for the registration
        Login user = Login.registerUser();

        //If user is registered, user logs in
        if (user.checkUserName() && user.checkPasswordComplexity() && user.checkCellPhoneNumber()) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter Username: ");
            String loginUsername = reader.nextLine();
            System.out.print("Enter Password: ");
            String loginPassword = reader.nextLine();
            String loginStatus = user.returnLoginStatus(loginUsername, loginPassword);
            System.out.println(loginStatus);
        }
    }
}