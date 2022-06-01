package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.models.Store;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidSQLException;
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
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM items WHERE id = ?");
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to delete an Item.");
        }
    }

    @Override
    public Item getById(String id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    public List<Item> getItemsFromStoreInStock( String id ) {
        List<Item> items = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM items WHERE store_id = (?) AND inventory != 0");
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

    public void updateItemQuantity(int quantity, String id) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE items SET inventory = ? WHERE id = ?");
            ps.setInt(1, quantity);
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to update the Item Quantity.");
        }
    }
    public void updateItemCost(int cost, String id) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE items SET cost = ? WHERE id = ?");
            ps.setInt(1, cost);
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to update the Item Cost.");
        }
    }
    public void updateItemName(String name, String id) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE items SET itemName = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to update the Item Name.");
        }
    }
    public void updateItemType(String type, String id) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE items SET itemType = ? WHERE id = ?");
            ps.setString(1, type);
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to update the Item Type.");        }
    }
    public void updateItemStore(String store_id, String id) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE items SET store_id = ? WHERE id = ?");
            ps.setString(1, store_id);
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to update the Item Quantity.");
        }
    }
    public Item getItemById(String id) {
        Item item = new Item();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM items WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                item = new Item(rs.getString("id"), rs.getString("itemname"),
                        rs.getString("itemtype"), rs.getString("itemdesc"),
                        rs.getInt("cost"), rs.getInt("inventory"),
                        rs.getString("store_id"));

        } catch (SQLException e) {
            throw new InvalidSQLException("An Error Occurred when trying to get Item by Item ID From the Database.");
        }

        return item;
    }

}
