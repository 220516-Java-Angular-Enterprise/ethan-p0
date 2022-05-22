package com.revature.fantasyAdventureStore;

import com.revature.fantasyAdventureStore.services.UserService;
import com.revature.fantasyAdventureStore.ui.StartMenu;

public class MainDriver {
    public static void main(String[] args) {

        // This is the start menu for my Fantasy Adventuring Store, here I will find out if the user wants to create
        // an account, login into an already existing account, or exit from the store.


        new StartMenu(new UserService()).start();
    }

}
