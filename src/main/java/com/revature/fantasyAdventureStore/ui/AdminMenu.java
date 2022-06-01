package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Item;
import com.revature.fantasyAdventureStore.models.OrderHistory;
import com.revature.fantasyAdventureStore.models.Store;
import com.revature.fantasyAdventureStore.services.*;
import com.revature.fantasyAdventureStore.util.annotations.Inject;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidUserException;

import java.util.*;
import java.util.stream.Collectors;

public class AdminMenu extends IMenu {

    @Inject
    private final Adventurer adv;
    private final UserService userService;
    private final ItemService itemService;
    private final StoreService storeService;
    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;

    @Inject
    public AdminMenu(Adventurer adv, UserService userService, ItemService itemService, StoreService storeService,
                     OrderService orderService, OrderHistoryService orderHistoryService) {
        this.adv = adv;
        this.userService = userService;
        this.itemService = itemService;
        this.storeService = storeService;
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
    }


    /*
        Start Admin Menu:
        [1] Select Store
        [2] Add New Store
        [3] Select Adventurer
        [4] Add New Adventurer
        [5] View Order History
        [x] Sign Out
     */
    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);
        displayAdminStartMsg(adv);

        sign_out:
        {
            while (true) {
                displayAdminMenu(adv);
                System.out.print("\nEnter: ");
                String input = scan.nextLine();
                System.out.println("\n------------------------------------------\n");
                switch (input) {
                    case "1":
                        // Go To Select Store Screen
                        selectStoreScreen();
                        break;
                    case "2":
                        // Go To Add Store Screen
                        System.out.print("Store Name: ");
                        String storeName = scan.nextLine();
                        System.out.print("Store Type: ");
                        String storeType = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        Store store = new Store(UUID.randomUUID().toString(), storeName, storeType);
                        storeService.addStore(store);
                        break;
                    case "3":
                        // Go To Select Adventurer Screen
                        AdventurerScreen();
                        break;
                    case "4":
                        String advName = "", store_id = "", advClass = "", password = "";

                        while (true) {

                            //Ask the user to create an Adventurer Name
                            System.out.print("\nAdventurer Name: ");
                            advName = scan.nextLine();

                            //Checks validity of the AdvName and makes sure it isn't already taken
                            try {
                                if (userService.isValidAdvName(advName)) {
                                    if (userService.isNotDuplicateUsername(advName)) break;
                                }
                            } catch (InvalidUserException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        while (true) {
                            System.out.print("\nPassword: ");
                            password = scan.nextLine();

                            try {
                                if (userService.isValidPassword(password)) {
                                    /* Asking user to enter in password again. */
                                    System.out.print("\nRe enter password again: ");
                                    String confirm = scan.nextLine();

                                    /* If the two password equals each other, break. Else re-enter password. */
                                    if (password.equals(confirm)) break;
                                    else System.out.println("Password does not match :(");
                                }
                            } catch (InvalidUserException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        while (true) {
                            // Asks the user to assign the class
                            displayAdventurerClasses();
                            System.out.print("\nAdventurer Class: ");
                            String advClassInput = scan.nextLine();
                            switch (advClassInput) {
                                case "1":
                                    advClass = "Barbarian";
                                    break;
                                case "2":
                                    advClass = "Bard";
                                    break;
                                case "3":
                                    advClass = "Cleric";
                                    break;
                                case "4":
                                    advClass = "Druid";
                                    break;
                                case "5":
                                    advClass = "Fighter";
                                    break;
                                case "6":
                                    advClass = "Monk";
                                    break;
                                case "7":
                                    advClass = "Paladin";
                                    break;
                                case "8":
                                    advClass = "Ranger";
                                    break;
                                case "9":
                                    advClass = "Rogue";
                                    break;
                                case "10":
                                    advClass = "Sorcerer";
                                    break;
                                case "11":
                                    advClass = "Warlock";
                                    break;
                                case "12":
                                    advClass = "Wizard";
                                    break;
                            }
                            store_id = userService.getStoreIdFromAdvRole(advClass);
                            break;
                        }
                        System.out.print("User Role Access: ");
                        String usrRole = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        Adventurer newAdv = new Adventurer(UUID.randomUUID().toString(), advName, advClass, advClass, usrRole, store_id);
                        userService.register(newAdv);

                        break;
                    case "5":
                        // Go To View Order History Screen
                        displayAllOrderHistory();
                        break;
                    case "x":
                        System.out.println("Have A Good Day " + adv.getAdvName());
                        break sign_out;
                    default:
                        System.out.println("Enter a valid input");
                        break;
                }
            }
        }
    }

    // Displays a List of the different Stores available and Lets the Admin pick a store
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
        if (stores.size() > page)
            System.out.println("\n[" + (1 + page) + "] " + stores.get(page).getStoreName() + "\t\t\t[" + stores.get(page).getStoreType() + "]");
        if (stores.size() > 1 + page)
            System.out.println("[" + (2 + page) + "] " + stores.get(1 + page).getStoreName() + "\t\t\t[" + stores.get(page + 1).getStoreType() + "]");
        if (stores.size() > 2 + page)
            System.out.println("[" + (3 + page) + "] " + stores.get(2 + page).getStoreName() + "\t\t\t[" + stores.get(page + 2).getStoreType() + "]");
        if (stores.size() > 3 + page)
            System.out.println("[" + (4 + page) + "] " + stores.get(3 + page).getStoreName() + "\t\t\t[" + stores.get(page + 3).getStoreType() + "]");
        if (stores.size() > 4 + page)
            System.out.println("[" + (5 + page) + "] " + stores.get(4 + page).getStoreName() + "\t\t\t[" + stores.get(page + 4).getStoreType() + "]");
        if (page > 0 && stores.size() > page + 5) System.out.println("[prev] \t [next] ");
        else if (page == 0 && stores.size() > 5) System.out.println("[next]");
        else if (page > 0 && stores.size() <= page + 5) System.out.println("[prev]");

        System.out.println("[x] Exit Store Selection");
        System.out.print("\nEnter: ");
    }

    private int chooseStoreFromList(List<Store> stores, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println("\n------------------------------------------\n");
        if (isInteger(input)) {
            int inputInt = Integer.parseInt(input);
            if (inputInt % 5 == 1) {
                if (stores.size() > page)
                    displayStore(stores.get(page));
                else System.out.println("Choose a valid Item");
            } else if (inputInt % 5 == 2) {
                if (stores.size() > page + 1)
                    displayStore(stores.get(page + 1));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 3) {
                if (stores.size() > page + 2)
                    displayStore(stores.get(page + 2));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 4) {
                if (stores.size() > page + 3)
                    displayStore(stores.get(page + 3));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 0) {
                if (stores.size() > page + 4)
                    displayStore(stores.get(page + 4));
                else System.out.println("Choose a valid Item Number");
            }
        } else if (input.equals("prev")) {
            if (page >= 5)
                page -= 5;
            else System.out.println("No Previous Page");
        } else if (input.equals("next")) {
            if (page + 5 < stores.size())
                page += 5;
            else System.out.println("No More Pages");
        } else if (input.equals("x")) {
            System.out.println("\nLeaving Store Selection");
            return -1;
        } else System.out.println("Please Enter a Valid Input");
        return page;
    }

    // After Selecting a Store prompts the Admin to choose what they want to do from inside that store.
    private void displayStore(Store store) {
        exit:
        {
            while (true) {
                System.out.println("\nWelcome to the " + store.getStoreName());
                System.out.println("What would you like to do?");
                System.out.println("[1] View Store Inventory");
                System.out.println("[2] Add New Item To Store Inventory");
                System.out.println("[3] Rename Store");
                System.out.println("[4] Change Store Type");
                System.out.println("[5] View Store Order History");
                System.out.println("[6] Delete Store");
                System.out.println("[x] Exit");

                Scanner scan = new Scanner(System.in);

                System.out.print("\nEnter: ");
                String input = scan.nextLine();
                System.out.println("\n------------------------------------------\n");
                switch (input) {
                    case "1":
                        // View Store Inventory
                        displayStoreItems(store);
                        break;
                    case "2":
                        // Add Item Screen
                        System.out.print("Item Name: ");
                        String itemName = scan.nextLine();
                        System.out.print("Item Type: ");
                        String itemType = scan.nextLine();
                        System.out.print("Item Desc: ");
                        String itemDesc = scan.nextLine();
                        System.out.print("Item Cost: ");
                        String itemCost = scan.nextLine();
                        int itemC = 0;
                        if (isInteger(itemCost)) itemC = Integer.parseInt(itemCost);
                        System.out.print("Item Inventory: ");
                        String itemInv = scan.nextLine();
                        int itemI = 0;
                        if (isInteger(itemInv)) itemI = Integer.parseInt(itemInv);

                        Item item = new Item(UUID.randomUUID().toString(), itemName, itemType, itemDesc, itemC, itemI, store.getId());
                        itemService.addItem(item);
                        System.out.println("\n------------------------------------------\n");
                        break;
                    case "3":
                        // Rename Store
                        System.out.println("What would you like to rename the store to?");
                        System.out.print("Enter: ");
                        String newName = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        storeService.updateStoreName(newName, store.getId());
                        break;
                    case "4":
                        // Change the Store Type
                        System.out.println("What would you like to make the store Type?");
                        System.out.print("Enter: ");
                        String newType = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        storeService.updateStoreType(newType, store.getId());
                        break;
                    case "5":
                        // View Store Order History
                        displayStoreOrderHistory(store);
                        break;
                    case "6":
                        // Delete the Store
                        storeService.deleteStore(store.getId());
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


    // If the Admin chooses to view the store inventory than it goes here to display all the items in that store.
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

    private void displayItemList(List<Item> items, int page) {
        if (items.size() > page)
            System.out.println("\n[" + (1 + page) + "] " + items.get(page).getItemName());
        if (items.size() > 1 + page)
            System.out.println("[" + (2 + page) + "] " + items.get(1 + page).getItemName());
        if (items.size() > 2 + page)
            System.out.println("[" + (3 + page) + "] " + items.get(2 + page).getItemName());
        if (items.size() > 3 + page)
            System.out.println("[" + (4 + page) + "] " + items.get(3 + page).getItemName());
        if (items.size() > 4 + page)
            System.out.println("[" + (5 + page) + "] " + items.get(4 + page).getItemName());
        if (page > 0 && items.size() > page + 5) System.out.println("[prev] \t[next]");
        else if (page == 0 && items.size() > 5) System.out.println("[next]");
        else if (page > 0 && items.size() <= page + 5) System.out.println("[prev]");

        System.out.println("[x] Exit Item Selection");
        System.out.print("\nEnter: ");
    }

    private int chooseItemFromList(List<Item> items, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println("\n------------------------------------------\n");
        if (isInteger(input)) {
            int inputInt = Integer.parseInt(input);
            if (inputInt % 5 == 1) {
                if (items.size() > page)
                    selectItem(items.get(page));
                else System.out.println("Choose a valid Item");
            } else if (inputInt % 5 == 2) {
                if (items.size() > page + 1)
                    selectItem(items.get(page + 1));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 3) {
                if (items.size() > page + 2)
                    selectItem(items.get(page + 2));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 4) {
                if (items.size() > page + 3)
                    selectItem(items.get(page + 3));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 0) {
                if (items.size() > page + 4)
                    selectItem(items.get(page + 4));
                else System.out.println("Choose a valid Item Number");
            }
        } else if (input.equals("prev")) {
            if (page >= 5)
                page -= 5;
            else System.out.println("No Previous Page.");
        } else if (input.equals("next")) {
            if (page + 5 < items.size())
                page += 5;
            else System.out.println("No Next Page.");
        } else if (input.equals("x")) {
            System.out.println("\nLeaving Item Selection");
            return -1;
        } else System.out.println("Please Enter a Valid Input");
        System.out.println("\n------------------------------------------\n");
        return page;
    }

    // After displaying the items from the store it asks what the Admin would like to do with that Item.
    private void selectItem(Item item) {
        Scanner scan = new Scanner(System.in);
        System.out.println(item.toString());
        exit:
        {
            while (true) {
                item = itemService.getItemByID(item.getId()); //Refreshes the Item
                System.out.println("What would you like to do with " + item.getItemName() + "?");
                System.out.println("[1] Update Quantity");
                System.out.println("[2] Change Item Name");
                System.out.println("[3] Change Item Type");
                System.out.println("[4] Change Item Cost");
                System.out.println("[5] Move the Item to a New Store");
                System.out.println("[6] Delete Item");
                System.out.println("[x] Exit");

                System.out.print("\nEnter: ");
                String input = scan.nextLine();
                System.out.println("\n------------------------------------------\n");

                switch (input) {
                    case "1":
                        // Update Item Quantity
                        System.out.println("What would you like to change the quantity to?");
                        System.out.print("Enter: ");
                        String newQuantity = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        if (isInteger(newQuantity))
                            itemService.updateItemQuantity(Integer.parseInt(newQuantity), item.getId());
                        break;
                    case "2":
                        // Change Item Name
                        System.out.println("What would you like to change the Item Name to?");
                        System.out.print("Enter: ");
                        String newName = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        itemService.updateItemName(newName, item.getId());
                        break;
                    case "3":
                        // Change Item Type
                        System.out.println("What would you like to change the Item Type to?");
                        System.out.print("Enter: ");
                        String newType = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        itemService.updateItemType(newType, item.getId());
                        break;
                    case "4":
                        // Change Item Cost
                        System.out.println("What would you like to change the Item Cost to?");
                        System.out.print("Enter: ");
                        String newCost = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        if (isInteger(newCost)) itemService.updateItemCost(Integer.parseInt(newCost), item.getId());
                        else System.out.println("Enter a Valid Number for the Item Cost");
                        break;
                    case "5":
                        // Move Item to a New Store
                        System.out.println("Which Store would you like to move the item to?");
                        System.out.print("Enter: ");
                        String newStore = scan.nextLine();
                        System.out.println("\n------------------------------------------\n");
                        Store store = storeService.getStoreByName(newStore);
                        itemService.updateItemStore(store.getId(), item.getId());
                        break;
                    case "6":
                        // Delete Item
                        itemService.deleteItem(item.getId());
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


    private void displayStoreOrderHistory(Store store) {
        // Display Order History for the Store make it, so you can sort the orders by date, latest to oldest
        // and by cost cheapest to most expensive
        Scanner scan = new Scanner(System.in);
        List<OrderHistory> orderHis = orderHistoryService.getOrderHistoryFromStore(store.getId());
        ;
        int page = 0;

        exit:
        {
            while (true) {
                if (page < -1) {
                    orderHis = sortOrderHistory(orderHis, page);
                    page = 0;
                } else {
                    orderHis = orderHistoryService.getOrderHistoryFromStore(store.getId());
                }
                displayOrderHistoryList(orderHis, page);
                page = chooseOrderHistoryFromList(orderHis, page);
                if (page == -1) break exit;
            }
        }
    }

    private void displayAllOrderHistory() {
        // Display Order History for the Store make it, so you can sort the orders by date, latest to oldest
        // and by cost cheapest to most expensive
        Scanner scan = new Scanner(System.in);
        List<OrderHistory> orderHis = orderHistoryService.getAll();
        int page = 0;

        exit:
        {
            while (true) {
                if (page < -1) {
                    orderHis = sortOrderHistory(orderHis, page);
                    page = 0;
                } else {
                    orderHis = orderHistoryService.getAll();
                }
                displayOrderHistoryList(orderHis, page);
                page = chooseOrderHistoryFromList(orderHis, page);
                if (page == -1) break exit;
            }
        }
    }

    private void displayAdvOrderHistory(Adventurer adv) {
        Scanner scan = new Scanner(System.in);
        List<OrderHistory> orderHis = orderHistoryService.getOrderHistoryFromAdv(adv.getId());
        int page = 0;

        exit:
        {
            while (true) {
                if (page < -1) {
                    orderHis = sortOrderHistory(orderHis, page);
                    page = 0;
                } else {
                    orderHis = orderHistoryService.getOrderHistoryFromAdv(adv.getId());
                }
                displayOrderHistoryList(orderHis, page);
                page = chooseOrderHistoryFromList(orderHis, page);
                if (page == -1) break exit;
            }
        }
    }

    private void displayOrderHistoryList(List<OrderHistory> orderHis, int page) {
        if (orderHis.size() > page)
            System.out.println("\n[" + (1 + page) + "] Store Name: " + orderHis.get(page).getStoreName() +
                    "\tItem Name: " + orderHis.get(page).getItemName() +
                    "\n Adventurer: " + orderHis.get(page).getAdvName() +
                    "\t Cost: " + orderHis.get(page).getCost() +
                    "\t Time: " + orderHis.get(page).getTime());
        if (orderHis.size() > 1 + page)
            System.out.println("[" + (2 + page) + "] Store Name: " + orderHis.get(page + 1).getStoreName() +
                    "\tItem Name: " + orderHis.get(page + 1).getItemName() +
                    "\n Adventurer: " + orderHis.get(page + 1).getAdvName() +
                    "\t Cost: " + orderHis.get(page + 1).getCost() +
                    "\t Time: " + orderHis.get(page + 1).getTime());
        if (orderHis.size() > 2 + page)
            System.out.println("[" + (3 + page) + "] Store Name: " + orderHis.get(page + 2).getStoreName() +
                    "\tItem Name: " + orderHis.get(page + 2).getItemName() +
                    "\n Adventurer: " + orderHis.get(page + 2).getAdvName() +
                    "\t Cost: " + orderHis.get(page + 2).getCost() +
                    "\t Time: " + orderHis.get(page + 2).getTime());
        if (orderHis.size() > 3 + page)
            System.out.println("[" + (4 + page) + "] Store Name: " + orderHis.get(page + 3).getStoreName() +
                    "\tItem Name: " + orderHis.get(page + 3).getItemName() +
                    "\n Adventurer: " + orderHis.get(page + 3).getAdvName() +
                    "\t Cost: " + orderHis.get(page + 3).getCost() +
                    "\t Time: " + orderHis.get(page + 3).getTime());
        if (orderHis.size() > 4 + page)
            System.out.println("[" + (5 + page) + "] Store Name: " + orderHis.get(page + 4).getStoreName() +
                    "\tItem Name: " + orderHis.get(page + 4).getItemName() +
                    "\n Adventurer: " + orderHis.get(page + 4).getAdvName() +
                    "\t Cost: " + orderHis.get(page + 4).getCost() +
                    "\t Time: " + orderHis.get(page + 4).getTime());
        if (page > 0 && orderHis.size() > page + 5) System.out.println("[prev] \t[next]");
        else if (page == 0 && orderHis.size() > 5) System.out.println("[next]");
        else if (page > 0 && orderHis.size() <= page + 5) System.out.println("[prev]");
        System.out.println("\n");
        System.out.println("[s1] Sort By Store Name \t [s2] Sort By Adventurer Name");
        System.out.println("[s3a] Sort By Item Cost (Least -> Most Expensive");
        System.out.println("[s3d] Sort By Item Cost (Most -> Least Expensive");
        System.out.println("[s4a] Sort By Date (Oldest -> Latest Purchase");
        System.out.println("[s4d] Sort By Date (Latest -> Oldest Purchase");
        System.out.println("[x] Exit Item Selection");
        System.out.print("\nEnter: ");
    }

    private int chooseOrderHistoryFromList(List<OrderHistory> orderHis, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println("\n------------------------------------------\n");
        if (isInteger(input)) {
            int inputInt = Integer.parseInt(input);
            if (inputInt % 5 == 1) {
                if (orderHis.size() > page)
                    selectOrderHistory(orderHis.get(page));
                else System.out.println("Choose a valid Item");
            } else if (inputInt % 5 == 2) {
                if (orderHis.size() > page + 1)
                    selectOrderHistory(orderHis.get(page + 1));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 3) {
                if (orderHis.size() > page + 2)
                    selectOrderHistory(orderHis.get(page + 2));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 4) {
                if (orderHis.size() > page + 3)
                    selectOrderHistory(orderHis.get(page + 3));
                else System.out.println("Choose a valid Item Number");
            } else if (inputInt % 5 == 0) {
                if (orderHis.size() > page + 4)
                    selectOrderHistory(orderHis.get(page + 4));
                else System.out.println("Choose a valid Item Number");
            }
        } else if (input.equals("prev")) {
            if (page >= 5)
                page -= 5;
            else System.out.println("No Previous Page.");
        } else if (input.equals("next")) {
            if (page + 5 < orderHis.size())
                page += 5;
            else System.out.println("No Next Page.");
        } else if (input.equals("s1")) {
            return -2;
        } else if (input.equals("s2")) {
            return -3;
        } else if (input.equals("s3a")) {
            return -4;
        } else if (input.equals("s3d")) {
            return -5;
        } else if (input.equals("s4a")) {
            return -6;
        } else if (input.equals("s4d")) {
            return -7;
        } else if (input.equals("x")) {
            System.out.println("\nLeaving Order History Selection");
            return -1;
        } else System.out.println("Please Enter a Valid Input");
        return page;
    }

    private void selectOrderHistory(OrderHistory orderHistory) {
        /*
            [1] Delete Order History
            [x] Exit
         */
        Scanner scan = new Scanner(System.in);
        System.out.println("\nStore Name: " + orderHistory.getStoreName() +
                "\tItem Name: " + orderHistory.getItemName() +
                "\t Adventurer: " + orderHistory.getAdvName() +
                "\t Cost: " + orderHistory.getCost() +
                "\t Time: " + orderHistory.getTime());

        exit:
        {
            while (true) {
                System.out.println("[1] Delete Order History");
                System.out.println("[x] Exit");
                System.out.print("Enter: ");
                String input = scan.nextLine();
                System.out.println("\n------------------------------------------\n");
                switch (input) {
                    case "x":
                        break exit;
                    case "1":
                        orderHistoryService.deleteOrderHistory(orderHistory.getId());
                        break exit;
                    default:
                        System.out.println("Enter a valid input");
                        break;
                }
            }
        }

    }

    private List<OrderHistory> sortOrderHistory(List<OrderHistory> orderHistories, int sortType) {
        switch (sortType) {
            case -2:
                // Sort by Store Name
                orderHistories = orderHistories.stream().sorted(Comparator.comparing(OrderHistory::getStoreName)).collect(Collectors.toList());
                break;
            case -3:
                // Sort by Adventurer Name
                orderHistories = orderHistories.stream().sorted(Comparator.comparing(OrderHistory::getAdvName)).collect(Collectors.toList());
                break;
            case -4:
                // Sort by Item Cost Ascending
                orderHistories = orderHistories.stream().sorted(Comparator.comparing(OrderHistory::getCost)).collect(Collectors.toList());
                break;
            case -5:
                // Sort by Item Cost Descending
                orderHistories = orderHistories.stream().sorted(Comparator.comparing(OrderHistory::getCost).reversed()).collect(Collectors.toList());
                break;
            case -6:
                //Sort by Item Purchase Time Ascending
                orderHistories = orderHistories.stream().sorted(Comparator.comparing(OrderHistory::getTime)).collect(Collectors.toList());
                break;
            case -7:
                //Sort by Item Purchase Time Ascending
                orderHistories = orderHistories.stream().sorted(Comparator.comparing(OrderHistory::getTime).reversed()).collect(Collectors.toList());
                break;
            default:
                System.out.println("Enter a valid input");
                break;

        }
        return orderHistories;
    }


    private void AdventurerScreen() {
        System.out.println("What would you like to do?");
        System.out.println("[1] List All Adventurers ");
        System.out.println("[2] Search for an Adventurer");
        System.out.println("[x] Exit");
        Scanner scan = new Scanner(System.in);


        System.out.print("\nEnter: ");
        String input = scan.nextLine();
        System.out.println("\n------------------------------------------\n");
        switch (input) {
            case "1":
                // List All the Items From the Store
                selectAdventurerScreen();
                break;
            case "2":
                // Search For an Item within the Store
                searchForAdventurer();
                break;
            case "x":
                return;
        }
    }

    private void selectAdventurerScreen() {
        int page = 0;
        exit:
        {
            while (true) {
                List<Adventurer> advs = userService.getAll();
                displayAdvList(advs, page);
                page = chooseAdvFromList(advs, page);
                if (page == -1) break exit;
            }
        }
    }
    private void displayAdvList(List<Adventurer> advs, int page) {
        if (advs.size() > page)
            System.out.println("\n[" + (1+page) + "] " + advs.get(page).getAdvName() + "\t\t\t[" + advs.get(page).getAdvRole() + "]");
        if (advs.size() > 1+page)
            System.out.println("[" + (2+page) + "] " + advs.get(1+page).getAdvName() + "\t\t\t[" + advs.get(page+1).getAdvRole() + "]");
        if (advs.size() > 2+page)
            System.out.println("[" + (3+page) + "] " + advs.get(2+page).getAdvName() + "\t\t\t[" + advs.get(page+2).getAdvRole() + "]");
        if (advs.size() > 3+page)
            System.out.println("[" + (4+page) + "] " + advs.get(3+page).getAdvName() + "\t\t\t[" + advs.get(page+3).getAdvRole() + "]");
        if (advs.size() > 4+page)
            System.out.println("[" + (5+page) + "] " + advs.get(4+page).getAdvName() + "\t\t\t[" + advs.get(page+4).getAdvRole() + "]");
        if (page > 0 && advs.size() > page+5) System.out.println("[prev] \t [next] ");
        else if (page == 0 && advs.size() > 5) System.out.println("[next]");
        else if (page > 0 && advs.size() <= page+5) System.out.println("[prev]");

        System.out.println("[x] Exit Adventurer Selection");
        System.out.print("\nEnter: ");
    }
    private int chooseAdvFromList(List<Adventurer> advs, int page) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println("\n------------------------------------------\n");
        if (isInteger(input)) {
            int inputInt = Integer.parseInt(input);
            if ( inputInt % 5 == 1 ) {
                if (advs.size() > page)
                    displayAdventurer(advs.get(page));
                else System.out.println("Choose a valid Item");
            }
            else if ( inputInt % 5 == 2 ) {
                if (advs.size() > page+1)
                    displayAdventurer(advs.get(page+1));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 3 ) {
                if (advs.size() > page+2)
                    displayAdventurer(advs.get(page+2));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 4 ) {
                if (advs.size() > page+3)
                    displayAdventurer(advs.get(page+3));
                else System.out.println("Choose a valid Item Number");
            }
            else if ( inputInt % 5 == 0 ) {
                if (advs.size() > page+4)
                    displayAdventurer(advs.get(page+4));
                else System.out.println("Choose a valid Item Number");
            }
        }
        else if (input.equals("prev")) {
            if (page >= 5)
                page -= 5;
            else System.out.println("No Previous Page");
        }
        else if (input.equals("next")) {
            if (page+5 < advs.size())
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
    private void displayAdventurer(Adventurer adv) {
        /*
            [1] Change User Role
            [2] View Order History
            [3] Delete User
            [x] Exit
         */
        exit:
        {
            while (true) {
                adv = userService.getAdvFromID(adv.getId());
                System.out.println("Adventurer Name: " + adv.getAdvName() + "\tClass: " + adv.getAdvRole() + "\tUser Role: " + adv.getUsrRole());
                System.out.println("[1] Change User Role");
                System.out.println("[2] View Order History");
                System.out.println("[3] Delete Adventurer");
                System.out.println("[x] Exit");

                Scanner scan = new Scanner(System.in);

                System.out.print("\nEnter: ");
                String input = scan.nextLine();
                System.out.println("\n------------------------------------------\n");
                String usrRole = "";
                switch (input) {
                    case "1":
                        exit_UsrRole:
                        {
                            while (true) {
                                System.out.println("[1] ADMIN");
                                System.out.println("[2] DEFAULT");
                                System.out.println("[x] Exit");
                                String usrRoleInput = scan.nextLine();
                                System.out.println("\n------------------------------------------\n");

                                switch (usrRoleInput) {
                                    case "1":
                                        usrRole = "ADMIN";
                                        break exit_UsrRole;
                                    case "2":
                                        usrRole = "DEFAULT";
                                        break exit_UsrRole;
                                    case "x":
                                        break exit;
                                    default:
                                        System.out.println("Insert a Valid Input");
                                        break;
                                }
                            }
                        }
                        userService.updateUsrRole(usrRole, adv.getId());
                        break;
                    case "2":
                        displayAdvOrderHistory(adv);
                        break;
                    case "3":
                        userService.deleteAdv(adv.getId());
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
    private void searchForAdventurer() {
        Scanner scan = new Scanner(System.in);
        int page = 0;

        exit_search:
        {
            while (true) {
                List<Adventurer> advs = userService.getAll();
                List<Adventurer> autoCompleteList = new ArrayList<>();
                System.out.println("\n[x] Exit");
                System.out.println("Search for an Adventurer by their Name");
                System.out.print("Enter: ");
                String input = scan.nextLine();
                System.out.println("\n------------------------------------------\n");
                if (input.equals("x")) break exit_search;

                for (int i = 0; i < advs.size(); i++) {
                    try {
                        if (input.equals(advs.get(i).getAdvName().substring(0, input.length())))
                            autoCompleteList.add(advs.get(i));
                    } catch (IndexOutOfBoundsException ignore) { }
                }
                exit:
                {
                    while (true) {
                        page = 0;
                        displayAdvList(autoCompleteList, page);
                        page = chooseAdvFromList(autoCompleteList, page);
                        if (page == -1) break exit;
                    }
                }

            }
        }
    }


    private void displayAdminStartMsg(Adventurer adv) {
        // "Welcome Warrior AdvName"
        System.out.println("\nWelcome Admin " +  adv.getAdvName());
        System.out.println("What Would You Like to Do?");
    }
    private void displayAdminMenu(Adventurer adv) {
        System.out.println("[1] Select a Store.");
        System.out.println("[2] Add a New Store.");
        System.out.println("[3] Select an Adventurer.");
        System.out.println("[4] Add a New Adventurer.");
        System.out.println("[5] View Order History.");
        System.out.println("[x] Sign-Out");
    }
    private void displayAdventurerClasses() {
        System.out.println("What is your Class?");
        System.out.println("[1] Barbarian \t\t [7] Paladin");
        System.out.println("[2] Bard \t\t [8] Ranger");
        System.out.println("[3] Cleric \t\t [9] Rogue");
        System.out.println("[4] Druid \t\t [10] Sorcerer");
        System.out.println("[5] Fighter \t\t [11] Warlock");
        System.out.println("[6] Monk \t\t [12] Wizard");
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
