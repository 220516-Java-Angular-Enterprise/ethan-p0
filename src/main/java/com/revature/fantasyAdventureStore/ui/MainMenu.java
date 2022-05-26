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


    /*
        Main Menu:
        [1] View "__________" Store
        [2] Select a different Store
        [3] View Cart
        [4] Checkout Cart
        [5] Sign out

            View Store:
                Display All Items in the Store:
                [?] Next Page
                [?] Prev Page
                [?] Leave Store

                Would you Like to add "____" to your cart?
                Quantity:

            Select a different Store:
                Display All the Shops
                [?] Go Back

                (Go To View Store)


     */


    @Override
    public void start() {
        System.out.println("\nWelcome to the main menu " + adv.getAdvName());



    }
}
