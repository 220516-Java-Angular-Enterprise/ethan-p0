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

    public Item getItemByID(String id) {
        return itemDAO.getItemById(id);
    }

}
