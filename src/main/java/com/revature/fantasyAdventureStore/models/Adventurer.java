package com.revature.fantasyAdventureStore.models;

public class Adventurer {

    /*
        Adventurer Attributes:
        String id: UUID
        String advName: (Username)
        String password:
        String advRole: (Warrior, Rogue, Paladin, Mage, etc.)
        String usrRole: (DEFAULT, ADMIN)

        The advRole will be used to recommend different shops to the customer
        For example: A warrior may be directed to a blacksmith or a Mage to a Library/Potion Shop
     */
    private String id, advName, password, advRole, usrRole, store_id;

    //Adventurer Constructors
    public Adventurer() { super(); }
    public Adventurer(String id, String advName, String password, String advRole, String usrRole, String store_id) {
        this.id = id;
        this.advName = advName;
        this.password = password;
        this.advRole = advRole;
        this.usrRole = usrRole;
        this.store_id = store_id;
    }

    //Getters and Setters:
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAdvName() { return advName; }
    public void setAdvName(String advName) { this.advName = advName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAdvRole() { return advRole; }
    public void setAdvRole(String role) { this.advRole = role; }
    public String getUsrRole() { return usrRole; }
    public void setUsrRole(String role) { this.usrRole = role; }
    public String getStore_id() { return store_id; }
    public void setStore_id(String store_id) { this.store_id = store_id; }

    // No Longer in use, was used to convert our users into a new file to temporarily store data.
    //public String toFileString() { return id + ":" + advName + ":" + password + ":" + advRole + ":" + usrRole + "\n"; }

    // Prints out the Adventurer and all of its attributes
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", advName='" + advName + '\'' +
                ", password='" + password + '\'' +
                ", advRole='" + advRole + '\'' +
                ", usrRole='" + usrRole + '\'' +
                ", store_id='" + store_id + '\'' +
                '}';
    }
}
