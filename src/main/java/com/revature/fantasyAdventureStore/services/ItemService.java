package com.revature.fantasyAdventureStore.services;

import com.revature.fantasyAdventureStore.daos.ItemDAO;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

import java.util.List;

public class ItemService {

    private final ItemDAO itemDAO;


    public ItemService(ItemDAO itemDAO) { this.itemDAO = itemDAO; }
    public List<Item> getItemsFromStoreInStock (String id) {
        return itemDAO.getItemsFromStoreInStock(id);
    }
    public void updateItemQuantity(int quantity, String id) {
        try {
            itemDAO.updateItemQuantity(quantity, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateItemCost(int cost, String id) {
        try {
            itemDAO.updateItemCost(cost, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateItemName(String name, String id) {
        try {
            itemDAO.updateItemName(name, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateItemType(String type, String id) {
        try {
            itemDAO.updateItemType(type, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateItemStore(String store_id, String id) {
        try {
            itemDAO.updateItemStore(store_id, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean deleteItem(String id) {
        try {
            itemDAO.delete(id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }
    public Item getItemByID(String id) {
        return itemDAO.getItemById(id);
    }
    public void addItem(Item item) { itemDAO.save(item); }


}
