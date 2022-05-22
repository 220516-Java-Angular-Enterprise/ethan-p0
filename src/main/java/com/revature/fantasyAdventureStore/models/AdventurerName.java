package com.revature.fantasyAdventureStore.models;

public class AdventurerName {

    //User Attributes:
    private String id;
    private String advName;
    private String password;
    private String role;

    //Constructors:
    public AdventurerName() { super(); }
    public AdventurerName(String id, String advName, String password, String role) {
        this.id = id;
        this.advName = advName;
        this.password = password;
        this.role = role;
    }

    //Getters and Setters:
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getadvName() { return advName; }
    public void setadvName(String advName) { this.advName = advName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    //To convert our user into a format our jank database can read it.
    //To be changed once we learn the new method
    public String toFileString() { return id + ":" + advName + ":" + password + ":" + role + "\n"; }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", advName='" + advName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
