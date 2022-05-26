package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.services.UserService;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

public class AdminMenu extends IMenu{

    //@Inject
    //private final Adventurer adv;
    private final Adventurer adv;
    private final UserService userService;

    public AdminMenu(Adventurer adv, UserService userService) {
        this.adv = adv;
        this.userService = userService;
    }


    @Override
    public void start() {
        System.out.println("\nWelcome to the ADMIN Menu");
    }
}