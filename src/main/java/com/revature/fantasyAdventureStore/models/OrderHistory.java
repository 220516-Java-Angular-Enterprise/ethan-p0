package com.revature.fantasyAdventureStore.models;

public class OrderHistory {

    /*
        OrderHistory Attributes:
        String id: UUID
        String time:
        String order_id:
        String adv_id:
    */
    private String id, time, order_id, adv_id;


    public OrderHistory(String id, String time, String order_id, String adv_id) {
        this.id = id;
        this.time = time;
        this.order_id = order_id;
        this.adv_id = adv_id;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getOrder_id() { return order_id; }
    public void setOrder_id(String order_id) { this.order_id = order_id; }
    public String getAdv_id() { return adv_id; }
    public void setAdv_id(String adv_id) { this.adv_id = adv_id; }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", order_id='" + order_id + '\'' +
                ", adv_id='" + adv_id + '\'' +
                '}';
    }
}
