package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.daos.AdvDAO;
import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.models.Order;
import com.revature.fantasyAdventureStore.models.Store;
import com.revature.fantasyAdventureStore.services.ItemService;
import com.revature.fantasyAdventureStore.services.OrderService;
import com.revature.fantasyAdventureStore.services.StoreService;
import com.revature.fantasyAdventureStore.services.UserService;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainMenu extends IMenu{

    @Inject
    private final Adventurer adv;
    private final UserService userService;
    private final ItemService itemService;
    private final StoreService storeService;
    private final OrderService orderService;

    @Inject
    public MainMenu(Adventurer adv, UserService userService, ItemService itemService, StoreService storeService, OrderService orderService) {
        this.adv = adv;
        this.userService = userService;
        this.itemService = itemService;
        this.storeService = storeService;
        this.orderService = orderService;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);
        displayMainStartMsg(adv);
        Store store = userService.getStoreByStoreID(adv.getStore_id());

        sign_out: {
            while (true) {
                displayMainMenu(adv);
                System.out.print("\nEnter: ");
                String input = scan.nextLine();
                switch (input) {
                    case "1":
                        // Go To Store Screen
                        displayStore(store);
                        break;
                    case "2":
                        // Go To Select Store Screen
                        selectStoreScreen();
                        break;
                    case "3":
                        // Go To View Cart
                        viewCart();
                        break;
                    case "4":
                        // Go To CheckOut
                        checkout();
                        break;
                    case "x":
                        System.out.println("\nStay Safe out there " + adv.getAdvName() + "\n");
                        break sign_out;
                }
            }
        }
    }
    /*
        Displays The Menu From Inside a Store
        "Welcome to STORENAME"
            "What would you like to do?"
            "[1] List All Items in Store"
            "[2] Search For Items in Store"
            "[x] Exit"
    */
    private void displayStore(Store store) {

        exit:
        {
            while (true) {
                System.out.println("\nWelcome to the " + store.getStoreName());
                System.out.println("What would you like to do?");
                System.out.println("[1] List All Items in " + store.getStoreName());
                System.out.println("[2] Search for an Item in the " + store.getStoreName());
                System.out.println("[x] Exit");
                Scanner scan = new Scanner(System.in);


                System.out.print("\nEnter: ");
                String input = scan.nextLine();
                switch (input) {
                    case "1":
                        // List All the Items From the Store
                        displayStoreItems(store);
                        break;
                    case "2":
                        // Search For an Item within the Store
                        searchForItems(store);
                        break;
                    case "x":
                        return;
                }
            }
        }
    }
    /*
        Displays A List of All Items from the Store:
     */
    private void displayStoreItems(Store store) {
        Scanner scan = new Scanner(System.in);
        int page = 0;

        exit:
        {
            while (true) {
                List<Item> items = itemService.getItemsFromStoreInStock(store.getId());
                displayItemList(items, page);
                page = chooseItemFromList(items, page);
                if (page == -1) break exit;
            }
        }
    }
    /*
        After an Item is Selected this menu is displayed asking what
        you would like to do next.
     */
    private void selectItem(Item item) {
        Scanner scan = new Scanner(System.in);
        System.out.println(item.toString());
        exit:
        {
            while (true) {
                System.out.println("Would you like to add " + item.getItemName() + " to your cart?");
                System.out.println("[y] yes \t[n] no");
                System.out.print("\nEnter: ");
                String input = scan.nextLine();

                switch (input) {
                    case "y":
                        System.out.println("How many would you like to add to your cart?");
                        String inputQuantity = scan.nextLine();
                        int quantity = Integer.parseInt(inputQuantity);
                        if (quantity > item.getInv()) {
                            System.out.println("There are not that many left in stock.");
                            System.out.println("There are only " + item.getInv() + " left.");
                            break;
                        }
                        orderService.addOrder("CART", quantity, adv.getId(), item.getId());
                        itemService.updateItemQuantity(item.getInv() - quantity, item.getId());
                        break exit;
                    case "n":
                        break exit;
                }
            }
        }
    }
    /*
        A Separate Function to actually display the list, so I can reuse it for searchForItems and displayStoreItems.
     */
    private void displayItemList(List<Item> items, int page) {
        if (items.size() > page)
            System.out.println("\n[" + (1+page) + "] " + items.get(page).getItemName());
        if (items.size() > 1+page)
            System.out.println("[" + (2+page) + "] " + items.get(1+page).getItemName());
        if (items.size() > 2+page)
            System.out.println("[" + (3+page) + "] " + items.get(2+page).getItemName());
        if (items.size() > 3+page)
            System.out.println("[" + (4+page) + "] " + items.get(3+page).getItemName());
        if (items.size() > 4+page)
            System.out.println("[" + (5+page) + "] " + items.get(4+page).getItemName());
        if (page > 0 && items.size() > page+5) System.out.println("[prev] \t[next]");
        else if (page == 0 && items.size() > 5) System.out.println("[next]");
        else if (page > 0 && items.size() <= page+5) System.out.println("[prev]");

        System.out.println("[x] Exit Item Selection");
        System.out.print("\nEnter: ");
    }
    /*
        A Separate Function to allow me to reuse code. Checks User input and calls selectItem
     */
    private int chooseItemFromList(List<Item> items, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if (isInteger(input)) {
            int inputInt = Integer.parseInt(input);
            if ( inputInt % 5 == 1 ) {
                if (items.size() > page)
                    selectItem(items.get(page));
                else System.out.println("Choose a valid Item");
            }
            else if ( inputInt % 5 == 2 ) {
                if (items.size() > page+1)
                    selectItem(items.get(page+1));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 3 ) {
                if (items.size() > page+2)
                    selectItem(items.get(page+2));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 4 ) {
                if (items.size() > page+3)
                    selectItem(items.get(page+3));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 0 ) {
                if (items.size() > page+4)
                    selectItem(items.get(page+4));
                else System.out.println("Choose a valid Item Number");
            }
        }
        else if (input.equals("prev")) {
            if (page >= 5)
                page -= 5;
            else System.out.println("No Previous Page.");
        }
        else if (input.equals("next")) {
            if (page+5 < items.size())
                page += 5;
            else System.out.println("No Next Page.");
        }
        else if (input.equals("x")) {
            System.out.println("\nLeaving Item Selection");
            return -1;
        }
        else System.out.println("Please Enter a Valid Input");
        return page;
    }
    /*
        A Function that allows the user to search for an Item by name, uses autocomplete function
        from the week2 challenge assignment.
     */
    private void searchForItems(Store store) {
        /*
            "Search for an Item by its name:
            "Be careful on your spelling"
            [x] Exit
            "Enter: "

            [1] Item 1
            [2] Item 2
            [3] Item 3
            [4] Item 4
            [5] Item 5
            [prev] [next]
            [x] Exit
            [Or Enter Any Text To Change The Search Criteria]

            Display List of Items with Auto Complete Search
            from the store similar to displayStoreItems.
         */
        Scanner scan = new Scanner(System.in);
        int page = 0;

        exit_search:
        {
            while (true) {
                List<Item> items = itemService.getItemsFromStoreInStock(store.getId());
                List<Item> autoCompleteList = new ArrayList<>();
                System.out.println("\n[x] Exit");
                System.out.println("Search for an Item by its Name");
                System.out.print("Enter: ");
                String input = scan.nextLine();
                if (input.equals("x")) break exit_search;

                for (int i = 1; i < items.size(); i++) {
                    try {
                        if (input.equals(items.get(i).getItemName().substring(0, input.length()))) autoCompleteList.add(items.get(i));
                    } catch (IndexOutOfBoundsException ignore) { }
                }
                exit:
                {
                    while (true) {
                        items = itemService.getItemsFromStoreInStock(store.getId());
                        displayItemList(autoCompleteList, page);
                        page = chooseItemFromList(autoCompleteList, page);
                        if (page == -1) break exit;
                    }
                }

            }
        }


    }
    private void selectStoreScreen() {
        /*
            [1] Store 1
            [2] Store 2
            [3] Store 3
            [4] Store 4
            [5] Store 5

            Page 1 out of n
            [back] Prev Page    [next] Next Page
            [x] Exit Select Store
            Enter:

            After selection go to displayStore menu
         */
        //List<store> store = StoreService
        int page = 0;
        exit:
        {
            while (true) {
                List<Store> stores = storeService.getAll();
                displayStoreList(stores, page);
                page = chooseStoreFromList(stores, page);
                if (page == -1) break exit;
            }
        }
    }
    private void displayStoreList(List<Store> stores, int page) {
        System.out.println("Which Store Would You Like To Shop At?");
        if (stores.size() > page)
            System.out.println("\n[" + (1+page) + "] " + stores.get(page).getStoreName() + "\t\t\t[" + stores.get(page).getStoreType() + "]");
        if (stores.size() > 1+page)
            System.out.println("[" + (2+page) + "] " + stores.get(1+page).getStoreName() + "\t\t\t[" + stores.get(page+1).getStoreType() + "]");
        if (stores.size() > 2+page)
            System.out.println("[" + (3+page) + "] " + stores.get(2+page).getStoreName() + "\t\t\t[" + stores.get(page+2).getStoreType() + "]");
        if (stores.size() > 3+page)
            System.out.println("[" + (4+page) + "] " + stores.get(3+page).getStoreName() + "\t\t\t[" + stores.get(page+3).getStoreType() + "]");
        if (stores.size() > 4+page)
            System.out.println("[" + (5+page) + "] " + stores.get(4+page).getStoreName() + "\t\t\t[" + stores.get(page+4).getStoreType() + "]");
        if (page > 0 && stores.size() > page+5) System.out.println("[prev] \t [next] ");
        else if (page == 0 && stores.size() > 5) System.out.println("[next]");
        else if (page > 0 && stores.size() <= page+5) System.out.println("[prev]");

        System.out.println("[x] Exit Store Selection");
        System.out.print("\nEnter: ");
    }
    private int chooseStoreFromList(List<Store> stores, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if (isInteger(input)) {
            int inputInt = Integer.parseInt(input);
            if ( inputInt % 5 == 1 ) {
                if (stores.size() > page)
                    displayStore(stores.get(page));
                else System.out.println("Choose a valid Item");
            }
            else if ( inputInt % 5 == 2 ) {
                if (stores.size() > page+1)
                    displayStore(stores.get(page+1));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 3 ) {
                if (stores.size() > page+2)
                    displayStore(stores.get(page+2));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 4 ) {
                if (stores.size() > page+3)
                    displayStore(stores.get(page+3));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 0 ) {
                if (stores.size() > page+4)
                    displayStore(stores.get(page+4));
                else System.out.println("Choose a valid Item Number");
            }
        }
        else if (input.equals("prev")) {
            if (page >= 5)
                page -= 5;
            else System.out.println("No Previous Page");
        }
        else if (input.equals("next")) {
            if (page+5 < stores.size())
                page += 5;
            else System.out.println("No More Pages");
        }
        else if (input.equals("x")) {
            System.out.println("\nLeaving Store Selection");
            return -1;
        }
        else System.out.println("Please Enter a Valid Input");
        return page;
    }
    private void viewCart() {
        /*
            Display Adventurer Orders with the status of in_cart


            [1] Select an Item
            [2] Go To Checkout
            [x] Exit
         */
        int page = 0;
        exit:
        {
            while (true) {
                List<Order> orders = orderService.getCart(adv.getId());
                displayCartItems(orders, page);
                page = chooseItemFromCart(orders, page);
                if (page == -1) break exit;
            }
        }
    }
    private void displayCartItems(List<Order> orders, int page) {
        Item item = new Item();
        System.out.println(adv.getAdvName() + " Cart:");
        if (orders.size() > page) {
            item = itemService.getItemByID(orders.get(page).getItem_id());
            System.out.println("\n[" + (1 + page) + "] " + item.getItemName() +
                    "\t\tQuantity: " + orders.get(page).getQuantity() + "\t\tCost: " +
                    item.getCost()*orders.get(page).getQuantity() + " Gold.");
        }
        if (orders.size() > 1 + page) {
            item = itemService.getItemByID(orders.get(page+1).getItem_id());
            System.out.println("\n[" + (2 + page) + "] " + item.getItemName() +
                    "\t\tQuantity: " + orders.get(page+1).getQuantity() + "\t\tCost: " +
                    item.getCost()*orders.get(page+1).getQuantity() + " Gold.");
        }   
        if (orders.size() > 2 + page) {
            item = itemService.getItemByID(orders.get(page+2).getItem_id());
            System.out.println("\n[" + (3 + page) + "] " + item.getItemName() +
                    "\t\tQuantity: " + orders.get(page+2).getQuantity() + "\t\tCost: " +
                    item.getCost()*orders.get(page+2).getQuantity() + " Gold.");
        }   
        if (orders.size() > 3 + page) {
            item = itemService.getItemByID(orders.get(page+3).getItem_id());
            System.out.println("\n[" + (4 + page) + "] " + item.getItemName() +
                    "\t\tQuantity: " + orders.get(page+3).getQuantity() + "\t\tCost: " +
                    item.getCost()*orders.get(page+3).getQuantity() + " Gold.");
        }
        if (orders.size() > 4+page) {
            item = itemService.getItemByID(orders.get(page+4).getItem_id());
            System.out.println("\n[" + (5 + page) + "] " + item.getItemName() +
                    "\t\tQuantity: " + orders.get(page+4).getQuantity() + "\t\tCost: " +
                    item.getCost()*orders.get(page+4).getQuantity() + " Gold.");
        }
        if (page > 0 && orders.size() > page+5) System.out.println("[prev] \t[next]");
        else if (page == 0 && orders.size() > 5) System.out.println("[next]");
        else if (page > 0 && orders.size() <= page+5) System.out.println("[prev]");

        System.out.println("[x] Exit Cart View");
        System.out.print("\nEnter: ");
    }
    private int chooseItemFromCart(List<Order> orders, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if (isInteger(input)) {
            int inputInt = Integer.parseInt(input);
            if ( inputInt % 5 == 1 ) {
                if (orders.size() > page)
                    selectItemFromCart(orders.get(page));
                else System.out.println("Choose a valid Order Number");
            }
            else if ( inputInt % 5 == 2 ) {
                if (orders.size() > page+1)
                    selectItemFromCart(orders.get(page+1));
                else System.out.println("Choose a valid Order Number");
            }
            else if ( inputInt % 5 == 3 ) {
                if (orders.size() > page+2)
                    selectItemFromCart(orders.get(page+2));
                else System.out.println("Choose a valid Order Number");
            }
            else if ( inputInt % 5 == 4 ) {
                if (orders.size() > page+3)
                    selectItemFromCart(orders.get(page+3));
                else System.out.println("Choose a valid Order Number");
            }
            else if ( inputInt % 5 == 0 ) {
                if (orders.size() > page+4)
                    selectItemFromCart(orders.get(page+4));
                else System.out.println("Choose a valid Order Number");
            }
        }
        else if (input.equals("prev")) {
            if (page >= 5)
                page -= 5;
            else System.out.println("No Previous Page");
        }
        else if (input.equals("next")) {
            if (page+5 < orders.size())
                page += 5;
            else System.out.println("No More Pages");
        }
        else if (input.equals("x")) {
            System.out.println("\nLeaving Order Selection");
            return -1;
        }
        else System.out.println("Please Enter a Valid Input");
        return page;
    }
    private void selectItemFromCart(Order order) {
        /*
            [1] Update Quantity
            [2] Delete Item
            [x] Exit
            Enter Number of Item:
         */
        System.out.println("In selectItemFromCart Congrats!!");
    }

    private void checkout() {
        /*
            Display Cart
            Display Total Cost
            [1] Confirm Purchase
            [2] Select Item
            [x] Exit
         */
    }

    private void displayMainStartMsg(Adventurer adv) {
        // "Welcome Warrior AdvName"
        System.out.println("\nWelcome " + adv.getAdvRole() + " " + adv.getAdvName());
        System.out.println("What Would You Like to Do?");
    }
    private void displayMainMenu(Adventurer adv) {
        System.out.println("[1] View " + userService.getStoreByStoreID(adv.getStore_id()).getStoreName() + ".");
        System.out.println("[2] Select A Different Store.");
        System.out.println("[3] View Cart.");
        System.out.println("[4] Checkout.");
        System.out.println("[x] Sign-Out");
    }

    private boolean isInteger( String input) {
        try {
            Integer.parseInt( input );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
