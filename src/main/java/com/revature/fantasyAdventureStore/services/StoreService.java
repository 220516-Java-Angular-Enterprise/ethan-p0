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
    public Store getStoreByName(String name) { return storeDAO.getStoreByName(name); }

    public void updateStoreName(String name, String id) {
        try {
            storeDAO.updateStoreName(name, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateStoreType(String type, String id) {
        try {
            storeDAO.updateStoreType(type, id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean deleteStore(String id) {
        try {
            storeDAO.delete(id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }
    public void addStore(Store store) { storeDAO.save(store); }
    public Store getStoreByID(String id) {
        return storeDAO.getById(id);
    }


}
