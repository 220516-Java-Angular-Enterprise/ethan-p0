package com.revature.fantasyAdventureStore.services;

import com.revature.fantasyAdventureStore.daos.ItemDAO;
import com.revature.fantasyAdventureStore.daos.OrderDAO;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) { this.orderDAO = orderDAO; }


    public void addOrder (String status, int quantity, String adv_id, String item_id) {
        Order order = new Order(UUID.randomUUID().toString(), status, quantity, item_id, adv_id );
        orderDAO.save(order);
    }
    public List<Order> getCart(String adv_id) {
        List<Order> orders = new ArrayList<>();
        return orderDAO.getCart(adv_id);
    }
    public Order getOrderByID(String id) {
        return orderDAO.getById(id);
    }

    public void updateOrderStatus(String status, String id) {
        try {
            orderDAO.updateOrderStatus(status, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateOrderQuantity(int quantity, String id) {
        try {
            orderDAO.updateOrderQuantity(quantity, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean deleteOrder(String id) {
        try {
            orderDAO.delete(id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }

}
