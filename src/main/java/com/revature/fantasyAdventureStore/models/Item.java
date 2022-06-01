package com.revature.fantasyAdventureStore.models;

public class Item {

    /*
        Item Attributes:
        String id: UUID
        String itemName:
        String itemType: (1h Weapon,2h Weapon, Armor, Potion, Wand, etc..)
        int cost: (300 Gold, 20 Gold)
        int inv: (5 in stock)
        String store_id:
     */

    private String id, itemName, itemType, itemDesc;
    private int cost, inv;
    private String store_id;


    public Item(String id, String itemName, String itemType, String itemDesc, int cost, int inv, String store_id) {
        this.id = id;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemDesc = itemDesc;
        this.cost = cost;
        this.inv = inv;
        this.store_id = store_id;
    }

    public Item() {

    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public String getItemDesc() { return itemDesc; }
    public void setItemDesc(String itemDesc) { this.itemDesc = itemDesc; }
    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }
    public int getInv() { return inv; }
    public void setInv(int inv) { this.inv = inv; }
    public String getStore_id() { return store_id; }
    public void setStore_id(String store_id) { this.store_id = store_id; }

    @Override
    public String toString() {
        return itemName + ": " + itemDesc+ ".\n" + "Cost: " + cost + " gold." + "In-Stock: " + inv + "\n";

        /* return "Item{" +
                "id='" + id + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", cost=" + cost +
                ", inv=" + inv +
                ", store_id='" + store_id + '\'' +
                '}'; */
    }

}
