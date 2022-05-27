package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.services.ItemService;
import com.revature.fantasyAdventureStore.services.StoreService;
import com.revature.fantasyAdventureStore.services.UserService;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

import java.util.Scanner;

public class MainMenu extends IMenu{

    @Inject
    private final Adventurer adv;
    private final UserService userService;
    private final ItemService itemService;
    private final StoreService storeService;

    @Inject
    public MainMenu(Adventurer adv, UserService userService, ItemService itemService, StoreService storeService) {
        this.adv = adv;
        this.userService = userService;
        this.itemService = itemService;
        this.storeService = storeService;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);
        displayMainStartMsg(adv);

        /*

         */
        sign_out: {
            while (true) {
                displayMainMenu(adv);
                System.out.println("\nEnter: ");
                String input = scan.nextLine();
                switch (input) {
                    case "1":
                        // Go To Store Screen
                        break;
                    case "2":
                        // Go To Select Store Screen
                        break;
                    case "3":
                        // Go To View Cart
                        break;
                    case "4":
                        // Go To CheckOut
                        break;
                    case "5":
                        System.out.println("Stay Safe out there " + adv.getAdvName());
                        break sign_out;
                }



            }
        }
    }

    public void displayMainStartMsg(Adventurer adv) {
        // "Welcome Warrior AdvName"
        System.out.println("\nWelcome " + adv.getAdvRole() + " " + adv.getAdvName());
        System.out.println("What Would You Like to Do?");
    }
    public void displayMainMenu(Adventurer adv) {
        System.out.println("[1] View " + userService.getStoreByStoreID(adv.getStore_id()) + " Store.");
        System.out.println("[2] Select A Different Store.");
        System.out.println("[3] View Cart.");
        System.out.println("[4] Checkout.");
        System.out.println("[5] Sign-Out");
    }
}
