package com.revature.fantasyAdventureStore.services;

import com.revature.fantasyAdventureStore.daos.*;
import com.revature.fantasyAdventureStore.models.OrderHistory;

import java.util.List;
import java.util.UUID;

public class OrderHistoryService {

    private final OrderHistoryDAO orderHistoryDAO;

    public OrderHistoryService(OrderHistoryDAO orderHistoryDAO) { this.orderHistoryDAO = orderHistoryDAO; }


    public void addOrderHistory (String time, String order_id, String adv_id) {
        OrderHistory orderHistory = new OrderHistory(UUID.randomUUID().toString(), time, order_id, adv_id, new ItemService(new ItemDAO()), new OrderService(new OrderDAO()), new UserService(new AdvDAO()), new StoreService(new StoreDAO()));
        orderHistoryDAO.save(orderHistory);
    }
    public List<OrderHistory> getOrderHistoryFromStore(String id) { return orderHistoryDAO.getOrderHistoryFromStore(id); }
    public List<OrderHistory> getOrderHistoryFromAdv(String id) { return orderHistoryDAO.getOrderHistoryFromAdv(id); }

    public boolean deleteOrderHistory(String id) {
        try {
            orderHistoryDAO.delete(id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }
    public List<OrderHistory> getAll() { return orderHistoryDAO.getAll(); }

}
