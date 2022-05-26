package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.StoreItemJunc;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StoreItemJuncDAO implements CrudDAO<StoreItemJunc>{

    // Establishes Database Connection
    Connection con = DatabaseConnection.getCon();

    /*
        Saves a New Store to the Postgresql Database.
        store: (id, storeName, storeType)
     */

    @Override
    public void save(StoreItemJunc storeItem) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO stores_items_junc (store_id, item_id) VALUES (?, ?)");
            ps.setString(1, storeItem.getStore_id());
            ps.setString(2, storeItem.getItem_id());
        } catch (SQLException e) {
            throw new RuntimeException("An Error occurred when trying to save a Store Item Junction to the database.");
        }
    }

    @Override
    public void update(StoreItemJunc obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public StoreItemJunc getById(String id) {
        return null;
    }

    @Override
    public List<StoreItemJunc> getAll() {
        return null;
    }
}
