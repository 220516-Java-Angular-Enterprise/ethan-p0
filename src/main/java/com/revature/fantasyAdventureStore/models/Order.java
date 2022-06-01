package com.revature.fantasyAdventureStore.models;

public class Order {

    /*

        Order Attributes:
        String id: UUID
        String time:
        String status:
        int quantity:
        String item_id:
        String adv_id:

    */

    private String id, status;
    private int quantity;
    private String item_id, adv_id;


    public Order(String id, String status, int quantity, String item_id, String adv_id) {
        this.id = id;
        this.status = status;
        this.quantity = quantity;
        this.item_id = item_id;
        this.adv_id = adv_id;
    }

    public Order() {}


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getItem_id() { return item_id; }
    public void setItem_id(String item_id) { this.item_id = item_id; }
    public String getAdv_id() { return adv_id; }
    public void setAdv_id(String adv_id) { this.adv_id = adv_id; }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", quantity=" + quantity +
                ", item_id='" + item_id + '\'' +
                ", adv_id='" + adv_id + '\'' +
                '}';
    }
}
