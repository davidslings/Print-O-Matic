package service;

import pojo.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserService {

    private Map<Integer, User> userDatabase;
    private Scanner scanner;

    public UserService() {
        this.userDatabase = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public Map<Integer, User> getUserDatabase() {
        return userDatabase;
    }

    // Method to create or update users with input validation.
    public User createUser(User user) {

        System.out.println("Please insert your first name:");
        String firstName = scanner.nextLine();

        while (firstName.isBlank() || firstName.isEmpty() || firstName.matches("\\d+")) {
            System.out.println("Name cannot be blank, null or contain numbers.");
            firstName = scanner.nextLine();
        } user.setFirstName(firstName);

        System.out.println("Please insert your last name:");
        String lastName = scanner.nextLine();
        while (lastName.isBlank() || lastName.isEmpty() || lastName.matches("\\d+")) {
            System.out.println("Name cannot be blank, null or contain numbers");
            lastName = scanner.nextLine();
        }
        user.setLastName(lastName);

        System.out.println("Please insert your street and house number:");
        String addressLine1 = scanner.nextLine();
        if (addressLine1.isBlank() || addressLine1.isEmpty()) {
            while (addressLine1.isBlank() || addressLine1.isEmpty()) {
                System.out.println("Address cannot be blank or null.");
                addressLine1 = scanner.nextLine();
            }
        } user.setAddressLine1(addressLine1);

        System.out.println("Please insert your zip code and city of residence:");
        String addressLine2 = scanner.nextLine();
        if (addressLine2.isBlank() || addressLine2.isEmpty()) {
            while (addressLine2.isBlank() || addressLine2.isEmpty()) {
                System.out.println("Address cannot be blank or null.");
                addressLine2 = scanner.nextLine();
            }
        } user.setAddressLine2(addressLine2);

        System.out.println("Please insert your email:");
        String email = scanner.nextLine();
        if (email.isBlank() || !email.contains("@")) {
            while (email.isBlank() || !email.contains("@")) {
                System.out.println("Your email cannot be blank or null and must contain the '@' character.");
                email = scanner.nextLine();
            }
        } user.setEmail(email);

        System.out.println("Please insert your phone number:");
        String phoneNumber = scanner.nextLine();
        while (phoneNumber.isBlank() || phoneNumber.isEmpty() || !isNumeric(phoneNumber)) {
            System.out.println("Your phone number cannot be blank or null, and must contain only numbers");
            phoneNumber = scanner.nextLine();
        }
        user.setPhoneNumber(phoneNumber);

        return user;
    }

    public User retrieveUser(int userID) {
        return new User(userDatabase.get(userID));
    }

    public void updateUser(User user) {
        userDatabase.put(user.getUserID(), user);
    }
    public void removeUser(User user) {
        this.userDatabase.remove(user.getUserID());
    }


    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            long l = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
