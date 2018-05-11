package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import java.util.UUID;

/**
 * Created by Montrell on 5/10/2018.
 */

public class User {
    private String fName;
    private String lName;
    private String email;
    private String address;
    private String city;
    private UUID id;
    public int userId;

    public User() {
        id = UUID.randomUUID();
    }

    public User(String fName, String lName, String email, String address, String city) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.address = address;
        this.city = city;

        id = UUID.randomUUID();
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public void setfName(String firstName) {
        this.fName = firstName;
    }

    public void setlName(String lastName) {
        this.lName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString() {
        return "User{" +
                "first name='" + fName + '\'' +
                ", last name='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", city=" + city +
                ", id=" + userId +
                '}';
    }
}