package pojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import service.LocalTimeTypeAdapter;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

public class User {

    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String email;
    private String phoneNumber;
    private int userID;


    public User() {
        this.firstName = null;
        this.lastName = null;
        this.addressLine1 = null;
        this.addressLine2 = null;
        this.email = null;
        this.phoneNumber = null;
        setUserID(checkUserCount());
    }

    public User(User source) {
        setFirstName(source.firstName);
        setLastName(source.lastName);
        setAddressLine1(source.addressLine1);
        setAddressLine2(source.addressLine2);
        setEmail(source.email);
        setPhoneNumber(source.phoneNumber);
        this.userID = source.userID;
    }

    public String getFirstName() {

        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserID() {
        return this.userID;
    }

    public static int checkUserCount() {

        // Specify the directory where JSON files are located
        Path fileDirectory = Paths.get("files");
        int userCount = 0;

        try {
            // Use Files.newDirectoryStream to get a list of JSON files in the directory
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fileDirectory, "*.json");
            // Iterate through each JSON file
            for (Path filePath : directoryStream) {
                    userCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userCount + 1;
    }

}
