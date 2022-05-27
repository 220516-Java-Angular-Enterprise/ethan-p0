package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<Item> getItemsByStoreID( String id ) {
        List<Item> items = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM items WHERE store_id = (?)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                items.add(new Item(rs.getString("id"), rs.getString("itemName"),
                        rs.getString("itemType"), rs.getString("itemDesc"),
                        rs.getInt("cost"), rs.getInt("inventory"),
                        rs.getString("store_id")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("An Error Occurred when trying to get Items by Store ID from the Database.");
        }

        return items;
    }
}
