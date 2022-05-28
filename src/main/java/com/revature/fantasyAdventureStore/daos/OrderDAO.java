package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Order;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements CrudDAO<Order>{

    // Establishes Database Connection
    Connection con = DatabaseConnection.getCon();

    /*
        Saves a New Order to the Postgresql Database.
        order: (id, time, status, quantity, item_id, adv_id)
    */
    @Override
    public void save(Order order) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO orders (id, status, quantity, item_id, adv_id) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, order.getId());
            ps.setString(2, order.getStatus());
            ps.setInt(3, order.getQuantity());
            ps.setString(4, order.getItem_id());
            ps.setString(5, order.getAdv_id());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An Error occurred when trying to save an Order to the database.");
        }
    }

    @Override
    public void update(Order obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Order getById(String id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    public List<Order> getCart( String adv_id) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE adv_id = ? AND status = 'CART'");
            ps.setString(1, adv_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(new Order(rs.getString("id"), rs.getString("status"),
                        rs.getInt("quantity"), rs.getString("item_id"),
                        rs.getString("adv_id")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("An Error Occurred When Trying to Recieve Cart for Adventurer");
        }
        return orders;
    }

}

