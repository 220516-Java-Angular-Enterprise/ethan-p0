package com.revature.fantasyAdventureStore.models;

public class Store {

    /*
        Store Attributes:
        String id: UUID
        String storeName:
        String storeType: (Blacksmith, Butchers Shop, General Store, etc..)
     */

    private String id, storeName, storeType;


    public Store(String id, String storeName, String storeType) {
        this.id = id;
        this.storeName = storeName;
        this.storeType = storeType;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getStoreType() { return storeType; }
    public void setStoreType(String storeType) { this.storeType = storeType; }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeType='" + storeType + '\'' +
                '}';
    }
}
