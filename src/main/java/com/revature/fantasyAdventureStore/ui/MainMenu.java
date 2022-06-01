package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.models.*;
import com.revature.fantasyAdventureStore.services.*;
import com.revature.fantasyAdventureStore.util.annotations.Inject;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu extends IMenu{

    @Inject
    private final Adventurer adv;
    private final UserService userService;
    private final ItemService itemService;
    private final StoreService storeService;
    private final OrderService orderService;

    private final OrderHistoryService orderHistoryService;

    @Inject
    public MainMenu(Adventurer adv, UserService userService, ItemService itemService, StoreService storeService,
                    OrderService orderService, OrderHistoryService orderHistoryService) {
        this.adv = adv;
        this.userService = userService;
        this.itemService = itemService;
        this.storeService = storeService;
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
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
                System.out.println("\n------------------------------------------\n");
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
                    default:
                        System.out.println("Enter a valid input");
                        break;
                }
            }
        }
    }

    // Displays all the items from within a store. Allows the user to select an item and add some quantity to their cart
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
                System.out.println("\n------------------------------------------\n");
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
                    default:
                        System.out.println("Enter a valid input");
                        break;
                }
            }
        }
    }
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
                System.out.println("\n------------------------------------------\n");
                switch (input) {
                    case "y":
                        System.out.println("How many would you like to add to your cart?");
                        String inputQuantity = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
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
                    default:
                        System.out.println("Enter a valid input");
                        break;
                }
            }
        }
    }
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
    private int chooseItemFromList(List<Item> items, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println("\n------------------------------------------\n");
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


    // Allows the user to input their own text and find items that start with the text by using the auto-complete
    // method we learned in the Week2 Coding Challenge.
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
                System.out.println("\n------------------------------------------\n");
                if (input.equals("x")) break exit_search;

                for (int i = 0; i < items.size(); i++) {
                    try {
                        if (input.equals(items.get(i).getItemName().substring(0, input.length()))) autoCompleteList.add(items.get(i));
                    } catch (IndexOutOfBoundsException ignore) { }
                }
                exit:
                {
                    while (true) {
                        page = 0;
                        displayItemList(autoCompleteList, page);
                        page = chooseItemFromList(autoCompleteList, page);
                        if (page == -1) break exit;
                    }
                }

            }
        }


    }



    // Displays a List of the different Stores available and Lets the user pick a store to visit
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
        System.out.println("\n------------------------------------------\n");
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


    // Displays a List of all the items from the users cart and lets the user pick an item from it or
    // got to the checkout.
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
        if (orders.size() > 0) System.out.println("[c] Go To Checkout");

        System.out.println("[x] Exit Cart View");
        System.out.print("\nEnter: ");
    }
    private int chooseItemFromCart(List<Order> orders, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println("\n------------------------------------------\n");
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
        else if (input.equals("c")) {
            checkout();
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
        exit: {
            while (true) {
                Item item = itemService.getItemByID(order.getItem_id());
                System.out.println("\n\n" + item.getItemName() +
                        "\t\tQuantity: " + order.getQuantity() + "\t\tCost: " +
                        item.getCost()*order.getQuantity() + " Gold.");
                System.out.println("\n[1] Update Quantity");
                System.out.println("[2] Remove Item From Cart");
                System.out.println("[x] Exit");
                Scanner scan = new Scanner(System.in);
                System.out.print("\nEnter: ");
                String input = scan.nextLine();
                System.out.println("\n------------------------------------------\n");

                switch (input) {
                    case "1":
                        System.out.println("What Would You Like to Change the Quantity To?");
                        System.out.print("\nEnter: ");
                        String quantity = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        if (isInteger(quantity)) {
                            int newQ = Integer.parseInt(quantity);
                            if (newQ == 0) {
                                // Delete Item From Cart.
                                itemService.updateItemQuantity(item.getInv() + order.getQuantity(), item.getId());
                                orderService.deleteOrder(order.getId());
                                break;
                            }
                            else if (newQ > item.getInv() + order.getQuantity()) {
                                System.out.println("There are not that many in Stock.");
                                break;
                            }
                            else {
                                itemService.updateItemQuantity((item.getInv() + order.getQuantity()) - newQ, item.getId());
                                orderService.updateOrderQuantity(newQ, order.getId());
                                order = orderService.getOrderByID(order.getId());
                                break;
                            }
                        } else System.out.println("Please Enter a Valid Integer.");
                        break;
                    case "2":
                        itemService.updateItemQuantity(item.getInv() + order.getQuantity(), item.getId());
                        orderService.deleteOrder(order.getId());
                        break exit;
                    case "x":
                        break exit;
                    default:
                        System.out.println("Enter a valid input");
                        break;

                }

            }
        }
    }



    // Displays a List of all the items in the users cart and asks them if they want to confirm it. Also allows the user
    // to select and item still and change the quantity and everything. Once they confirm the order it creates order_history
    // objects and stores it and changes the order status to PURCHASED.
    private void checkout() {
        /*
            Display Cart
            Display Total Cost
            [1] Order 1
            [2] Order 2
            [n] Order n
            [confirm]
            [x] Exit

            // Displays the Current Items in the Cart allows the user to
         */
        int page = 0;
        exit:
        {
            while (true) {
                List<Order> orders = orderService.getCart(adv.getId());
                displayCheckOutItems(orders, page);
                page = chooseFromCheckout(orders, page);
                if (page == -1) break exit;
            }
        }


    }
    private void displayCheckOutItems(List<Order> orders, int page) {
        Item item = new Item();
        int totalCost = 0;
        for (Order o : orders) {
            totalCost += itemService.getItemByID(o.getItem_id()).getCost();
        }

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
        if (orders.size() > 0) System.out.println("[confirm]");
        System.out.println("Total Cost: " + totalCost + " Gold.");
        System.out.println("[x] Exit Checkout");
        System.out.print("\nEnter: ");
    }
    private int chooseFromCheckout(List<Order> orders, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println("\n------------------------------------------\n");
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
        else if (input.equals("confirm")) {
            System.out.println("Thank You For your Purchase");
            purchaseCart(orders);
            return -1;
        }
        else if (input.equals("x")) {
            System.out.println("\nLeaving Order Selection");
            return -1;
        }
        else System.out.println("Please Enter a Valid Input");
        return page;
    }
    private void purchaseCart(List<Order> orders) {
        // Create an Order History and Change Status To PURCHASED
        for (Order order : orders ) {
            Timestamp time = Timestamp.from(Instant.now());
            orderHistoryService.addOrderHistory(String.valueOf(time), order.getId(), adv.getId());
            orderService.updateOrderStatus("PURCHASED", order.getId());
        }
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


/*
    Things I want to do to pretty up the main menu:

    Item/Store/Order Lists:
    _________________________________________________
    | [1] Item 1    |   Item Desc   |   Item Cost   |
    -------------------------------------------------
    | [2] Item 2    |               |               |
    -------------------------------------------------
    | [3] Item 3    |               |               |
    -------------------------------------------------
        [prev]         3 out of 10             [next]
    [x] Exit
    Enter:

    After Every User Entry Think of it as going to a new
    page. I want a border like:
    ---------------------------------------------------
    To cut it off and start the next page.

 */