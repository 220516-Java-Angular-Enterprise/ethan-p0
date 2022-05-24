package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

public class MainMenu extends IMenu{

    @Inject
    private final Adventurer adv;

    @Inject
    public MainMenu(Adventurer adv) {
        this.adv = adv;
    }

    @Override
    public void start() {
        System.out.println("\nWelcome to the main menu " + adv.getAdvName());
    }
}
