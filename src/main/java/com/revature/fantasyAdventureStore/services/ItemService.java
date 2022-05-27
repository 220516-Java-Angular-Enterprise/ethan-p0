package com.revature.fantasyAdventureStore.services;

import com.revature.fantasyAdventureStore.daos.ItemDAO;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

import java.util.List;

public class ItemService {

    private final ItemDAO itemDAO;

    public ItemService(ItemDAO itemDAO) { this.itemDAO = itemDAO; }

    public List<Item> getItemsByStore (String id) {
        return itemDAO.getItemsByStoreID(id);

    }

}
