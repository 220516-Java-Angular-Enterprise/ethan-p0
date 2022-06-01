package com.revature.fantasyAdventureStore.models;

import com.revature.fantasyAdventureStore.daos.AdvDAO;
import com.revature.fantasyAdventureStore.daos.ItemDAO;
import com.revature.fantasyAdventureStore.daos.OrderDAO;
import com.revature.fantasyAdventureStore.daos.StoreDAO;
import com.revature.fantasyAdventureStore.services.ItemService;
import com.revature.fantasyAdventureStore.services.OrderService;
import com.revature.fantasyAdventureStore.services.StoreService;
import com.revature.fantasyAdventureStore.services.UserService;

public class OrderHistory {

    /*
        OrderHistory Attributes:
        String id: UUID
        String time:
        String order_id:
        String adv_id:
    */
    private String id, time, order_id, adv_id;
    private final ItemService itemService;
    private final OrderService orderService;
    private final UserService userService;
    private final StoreService storeService;


    public OrderHistory(String id, String time, String order_id, String adv_id, ItemService itemService, OrderService orderService, UserService userService, StoreService storeService) {
        this.id = id;
        this.time = time;
        this.order_id = order_id;
        this.adv_id = adv_id;
        this.itemService = itemService;
        this.orderService = orderService;
        this.userService = userService;
        this.storeService = storeService;
    }

    public OrderHistory() {
        this.itemService = new ItemService(new ItemDAO());
        this.orderService = new OrderService(new OrderDAO());
        this.userService = new UserService(new AdvDAO());
        this.storeService = new StoreService(new StoreDAO());
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getOrder_id() { return order_id; }
    public void setOrder_id(String order_id) { this.order_id = order_id; }
    public String getAdv_id() { return adv_id; }
    public void setAdv_id(String adv_id) { this.adv_id = adv_id; }

    public int getCost() {
        return itemService.getItemByID(orderService.getOrderByID(order_id).getItem_id()).getCost();
    }
    public String getAdvName() {
        return userService.getAdvFromID(adv_id).getAdvName();
    }
    public String getStoreName() {
        return storeService.getStoreByID(itemService.getItemByID(orderService.getOrderByID(order_id).getItem_id()).getStore_id()).getStoreName();
    }
    public String getItemName() {
        return itemService.getItemByID(orderService.getOrderByID(order_id).getItem_id()).getItemName();
    }

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
