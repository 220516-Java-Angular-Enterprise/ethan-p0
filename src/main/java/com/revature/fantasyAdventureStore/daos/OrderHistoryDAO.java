package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.OrderHistory;
import com.revature.fantasyAdventureStore.services.ItemService;
import com.revature.fantasyAdventureStore.services.OrderService;
import com.revature.fantasyAdventureStore.services.StoreService;
import com.revature.fantasyAdventureStore.services.UserService;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidSQLException;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            ps.setString(3, orderH.getOrder_id());
            ps.setString(4, orderH.getAdv_id());
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
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM order_history WHERE id = ?");
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to delete an Order History.");
        }
    }

    @Override
    public OrderHistory getById(String id) {
        return null;
    }

    @Override
    public List<OrderHistory> getAll() {
        List<OrderHistory> orderHis = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM order_history");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderHistory orderH = new OrderHistory();
                orderH.setId(rs.getString("id"));
                orderH.setTime(rs.getString("time"));
                orderH.setOrder_id(rs.getString("order_id"));
                orderH.setAdv_id(rs.getString("adv_id"));


                orderHis.add(orderH);
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to get data from to the database.");
        }

        return orderHis;
    }

    public List<OrderHistory> getOrderHistoryFromStore(String id ) {
        List<OrderHistory> orderhistory = new ArrayList<>();

        try {
            //PreparedStatement ps = con.prepareStatement("SELECT * FROM order_history join orders on orders.id = order_history.order_id join items on items.id = orders.item_id WHERE store_id = (?)");
            PreparedStatement ps = con.prepareStatement("select * from order_history \n" +
                                                            "join orders on orders.id = order_history.order_id\n" +
                                                            "join items on items.id = orders.item_id\n" +
                                                            "where store_id = (?)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orderhistory.add(new OrderHistory(rs.getString("id"), rs.getString("time"),
                        rs.getString("order_id"), rs.getString("adv_id"), new ItemService(new ItemDAO()), new OrderService(new OrderDAO()), new UserService(new AdvDAO()), new StoreService(new StoreDAO())));
            }

        } catch (SQLException e) {
            throw new RuntimeException("An Error Occurred when trying to get orderhistory by Store ID from the Database.");
        }

        return orderhistory;
    }
    public List<OrderHistory> getOrderHistoryFromAdv(String id ) {
        List<OrderHistory> orderhistory = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("select * from order_history where adv_id = (?)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orderhistory.add(new OrderHistory(rs.getString("id"), rs.getString("time"),
                        rs.getString("order_id"), rs.getString("adv_id"), new ItemService(new ItemDAO()), new OrderService(new OrderDAO()), new UserService(new AdvDAO()), new StoreService(new StoreDAO())));
            }

        } catch (SQLException e) {
            throw new RuntimeException("An Error Occurred when trying to get orderhistory by Adv ID from the Database.");
        }

        return orderhistory;
    }
}
