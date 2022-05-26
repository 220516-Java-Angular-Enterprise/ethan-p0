package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ItemDAO implements CrudDAO<Item> {

    // Establishes Database Connection
    Connection con = DatabaseConnection.getCon();

    /*
        Saves a New Adventurer to the Postgresql Database.
        item: (id, itemName, itemType, itemDesc, cost, inv, store_id)
    */
    @Override
    public void save(Item item) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO items (id, itemname, itemtype, itemdesc, cost, inventory, store_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, item.getId());
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getItemType());
            ps.setString(4, item.getItemDesc());
            ps.setInt(5, item.getCost());
            ps.setInt(6, item.getInv());
            ps.setString(7, item.getStore_id());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An Error occurred when trying to save an Item to the database.");
        }
    }

    @Override
    public void update(Item obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Item getById(String id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }
}
