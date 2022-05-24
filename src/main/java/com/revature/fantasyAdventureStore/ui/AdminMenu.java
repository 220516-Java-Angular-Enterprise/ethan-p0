package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

public class AdminMenu extends IMenu{

    //@Inject
    //private final Adventurer adv;

    @Inject
    public AdminMenu() { }

    @Override
    public void start() {
        System.out.println("\nWelcome to the ADMIN Menu");
    }
}