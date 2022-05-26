package com.revature.fantasyAdventureStore.models;

public class StoreItemJunc {

    /*
        StoreItemJunc Attributes:
        String store_id:
        String item_id:
     */

    private String store_id, item_id;


    public StoreItemJunc(String store_id, String item_id) {
        this.store_id = store_id;
        this.item_id = item_id;
    }

    public String getStore_id() { return store_id; }
    public void setStore_id(String store_id) { this.store_id = store_id; }
    public String getItem_id() { return item_id; }
    public void setItem_id(String item_id) { this.item_id = item_id; }

    @Override
    public String toString() {
        return "StoreItemJunc{" +
                "store_id='" + store_id + '\'' +
                ", item_id='" + item_id + '\'' +
                '}';
    }
}
