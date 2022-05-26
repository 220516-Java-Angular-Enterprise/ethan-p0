package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Store;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            throw new RuntimeException("An Error occurred when trying to save a Store to the database.");
        }
    }

    @Override
    public void update(Store obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Store getById(String id) {
        return null;
    }

    @Override
    public List<Store> getAll() {
        return null;
    }
}
