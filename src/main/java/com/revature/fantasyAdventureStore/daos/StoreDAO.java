package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Store;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidSQLException;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO implements CrudDAO<Store>{

    // Establishes Database Connection
    Connection con = DatabaseConnection.getCon();

    /*
        Saves a New Store to the Postgresql Database.
        store: (id, storeName, storeType)
     */

    @Override
    public void save(Store store) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO stores (id, storename, storetype) VALUES (?, ?, ?)");
            ps.setString(1, store.getId());
            ps.setString(2, store.getStoreName());
            ps.setString(3, store.getStoreType());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("An Error occurred when trying to save a Store to the database.");
        }
    }

    @Override
    public void update(Store obj) {

    }

    @Override
    public void delete(String id) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM stores WHERE id = ?");
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to delete a Store.");
        }
    }

    @Override
    public Store getById(String id) {
        Store store = new Store();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM stores WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                store = new Store(rs.getString("id"), rs.getString("storename"), rs.getString("storetype"));

        } catch(SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to get Store by Store ID from Adventure in the Database.");
        }
        return store;
    }
    @Override
    public List<Store> getAll() {
        List<Store> stores = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM stores");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Store store = new Store();
                store.setId(rs.getString("id"));
                store.setStoreName(rs.getString("storeName"));
                store.setStoreType(rs.getString("storeType"));

                stores.add(store);
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to get Stores from to the database.");
        }
        return stores;
    }
    public Store getStoreByName(String name) {
        Store store = new Store();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM stores WHERE storename = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                store = new Store(rs.getString("id"), rs.getString("storename"), rs.getString("storetype"));

        } catch(SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to get Store by Store ID from Adventure in the Database.");
        }
        return store;
    }

    public void updateStoreName(String name, String id) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE stores SET storename = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to update the Store Name.");
        }
    }
    public void updateStoreType(String type, String id) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE stores SET storetype = ? WHERE id = ?");
            ps.setString(1, type);
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to update the Store Type.");        }
    }

}
