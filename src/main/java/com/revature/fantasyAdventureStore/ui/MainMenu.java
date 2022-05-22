package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.models.AdventurerName;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

public class MainMenu extends IMenu{

    @Inject
    private final AdventurerName adv;

    @Inject
    public MainMenu(AdventurerName adv) {
        this.adv = adv;
    }

    @Override
    public void start() {
        System.out.println("\nWelcome to the main menu " + adv.getadvName());
    }
}
