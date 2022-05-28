package com.revature.fantasyAdventureStore.services;

import com.revature.fantasyAdventureStore.daos.StoreDAO;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.models.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreService {

    private final StoreDAO storeDAO;

    public StoreService(StoreDAO storeDAO) { this.storeDAO = storeDAO; }

    public List<Store> getAll() { return storeDAO.getAll();}

}
