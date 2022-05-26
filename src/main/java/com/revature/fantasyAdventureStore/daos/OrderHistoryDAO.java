package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.OrderHistory;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderHistoryDAO implements CrudDAO<OrderHistory>{
    // Establishes Database Connection
    Connection con = DatabaseConnection.getCon();


    /*
         Saves a New OrderHistory to the Postgresql Database.
         order_history: (id, time, order_id, adv_id)
    */
    @Override
    public void save(OrderHistory orderH) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO order_history (id, time, order_id, adv_id) VALUES (?, ?, ?, ?)");
            ps.setString(1, orderH.getId());
            ps.setString(2, orderH.getTime());
            ps.setString(5, orderH.getOrder_id());
            ps.setString(6, orderH.getAdv_id());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An Error occurred when trying to save an Order History to the database.");
        }
    }

    @Override
    public void update(OrderHistory obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public OrderHistory getById(String id) {
        return null;
    }

    @Override
    public List<OrderHistory> getAll() {
        return null;
    }
}
