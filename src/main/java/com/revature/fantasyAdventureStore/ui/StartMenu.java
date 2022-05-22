package com.revature.fantasyAdventureStore.ui;

import com.revature.fantasyAdventureStore.models.AdventurerName;
import com.revature.fantasyAdventureStore.services.UserService;
import com.revature.fantasyAdventureStore.util.annotations.Inject;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidUserException;
import com.sun.source.tree.WhileLoopTree;

import java.util.Scanner;
import java.util.UUID;

/*
                        /\
                        ||
                        ||
                        ||
                        ||                                               ~-----~
                        ||                                            /===--  ---~~~
                        ||                   ;'                 /==~- --   -    ---~~~
                        ||                (/ ('              /=----         ~~_  --(  '
                        ||             ' / ;'             /=----               \__~
     '                ~==_=~          '('             ~-~~      ~~~~        ~~~--\~'
     \\                (c_\_        .i.             /~--    ~~~--   -~     (     '
      `\               (}| /       / : \           / ~~------~     ~~\   (
      \ '               ||/ \      |===|          /~/             ~~~ \ \(
      ``~\              ~~\  )~.~_ >._.< _~-~     |`_          ~~-~     )\
       '-~                 {  /  ) \___/ (   \   |` ` _       ~~         '
       \ -~\                -<__/  -   -  L~ -;   \\    \ _ _/
       `` ~~=\                  {    :    }\ ,\    ||   _ :(
        \  ~~=\__                \ _/ \_ /  )  } _//   ( `|'
        ``    , ~\--~=\           \     /  / _/ / '    (   '
         \`    } ~ ~~ -~=\   _~_  / \ / \ )^ ( // :_  / '
         |    ,          _~-'   '~~__-_  / - |/     \ (
          \  ,_--_     _/              \_'---', -~ .   \
           )/      /\ / /\   ,~,         \__ _}     \_  "~_
           ,      { ( _ )'} ~ - \_    ~\  (-:-)       "\   ~
                  /'' ''  )~ \~_ ~\   )->  \ :|    _,       "
                 (\  _/)''} | \~_ ~  /~(   | :)   /          }
                <``  >;,,/  )= \~__ {{{ '  \ =(  ,   ,       ;
               {o_o }_/     |v  '~__  _    )-v|  "  :       ,"
               {/"\_)       {_/'  \~__ ~\_ \\_} '  {        /~\
               ,/!          '_/    '~__ _-~ \_' :  '      ,"  ~
              (''`                  /,'~___~    | /     ,"  \ ~'
             '/, )                 (-)  '~____~";     ,"     , }
           /,')                    / \         /  ,~-"       '~'
       (  ''/                     / ( '       /  /          '~'
    ~ ~  ,, /) ,                 (/( \)      ( -)          /~'
  (  ~~ )`  ~}                   '  \)'     _/ /           ~'
 { |) /`,--.(  }'                    '     (  /          /~'
(` ~ ( c|~~| `}   )                        '/:\         ,'
 ~ )/``) )) '|),                          (/ | \)
  (` (-~(( `~`'  )                        ' (/ '
   `~'    )'`')                              '
     ` ``

 */


public class StartMenu extends IMenu{

    @Inject
    private final UserService userService;

    @Inject
    public StartMenu(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void start() {
        // User Input:
        Scanner scan = new Scanner(System.in);

        displayASCIIArt();
        displayStartMessage();

        exit_store: {
            //Loops through the start menu until the user chooses to exit from the program.
            while (true) {
                displayLoginMessage();

                System.out.print("\nEnter: ");
                String input = scan.nextLine();

                switch(input) {
                    case "1":
                        // Go to the login method
                        login();
                        break;
                    case "2":
                        // Go to the Sign-up method
                        signup();
                        break;
                    case "3":
                        System.out.println("Good Luck Adventurer!");
                        break exit_store;
                    default:
                        System.out.println("\nInvalid input.");
                        break;
                }
            }
        }
    }

    private void login() {

    }

    private void signup() {
        // Signup function:
        // Enter a advName and a password. Make sure they are not already taken and
        // make sure they meet the requirments for the advName and password.
        String advName;
        String password;
        Scanner scan = new Scanner(System.in);

        completeSignup:
        {
            // Loop Through Sign-up until successful account creation
            // Ask the user for a advName
            // Check to make sure the advName is a valid advName and has not been taken
            // (Don't know how to do that last part yet)
            //
            // Ask the user for a Password
            // Repeat above steps
            //
            // Ask user to confirm account credentials
            //
            //
            while (true) {
                //Ask the user to create an Adventurer Name
                System.out.print("\nAdventurer Name: ");
                advName = scan.nextLine();

                //Checks validity of the AdvName and makes sure it isn't already taken
                try {
                    if (userService.isValidAdvName(advName)) {
                        //Check if duplicate Adv Name
                        break;
                    }
                } catch (InvalidUserException e) {
                    System.out.println(e.getMessage());
                }
            }

            //Asking and Checking the Password
            while (true) {
                System.out.println("\nPassword: ");
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

            confirmExit:
            {
                while (true) {
                    /* Asking user to confirm username and password. */
                    System.out.println("\nPlease confirm your credentials (y/n)");
                    System.out.println("\nUsername: " + advName);
                    System.out.println("Password: " + password);

                    System.out.print("\nEnter: ");
                    String input = scan.nextLine();

                    /* Switch statement for user input. Basically yes or no. */
                    switch (input) {
                        case "y":
                            /* If yes, we instantiate a User object to store all the information into it. */
                            AdventurerName adv = new AdventurerName(UUID.randomUUID().toString(), advName, password, "DEFAULT");

                            //ONCE THE DATA BASE IS SET UP
                            //UserService.register(adv);

                            /* Calling the anonymous class MainMenu.start() to navigate to the main menu screen. */
                            /* We are also passing in a user object, so we know who is logged in. */
                            new MainMenu(adv).start();

                            /* Break out of the entire loop. */
                            break completeSignup;
                        case "n":
                            /* Re-enter in credentials again. */
                            break confirmExit;
                        default:
                            System.out.println("Invalid Input.");
                            break;
                    }
                }
            }

        }
    }

    private void displayStartMessage() {
        System.out.println("Welcome to the best Fantasy Adventure store in all the realms!");
        System.out.println("Here you can find anything you may need on your adventures; whether that be");
        System.out.println("weapons, armour, traps, potions, food, or magical books and scrolls.");
        System.out.println("We have all that and more and you won't find a better deal on your adventuring gear than here!");
    }

    private void displayLoginMessage() {
        System.out.println("[1] Login");
        System.out.println("[2] Signup");
        System.out.println("[3] Exit");
    }

    private void displayASCIIArt() {
        System.out.println("                        |\\");
        System.out.println("                        ||");
        System.out.println("                        ||");
        System.out.println("                        ||");
        System.out.println("                        ||                                               ~-----~");
        System.out.println("                        ||                                            /===--  ---~~~");
        System.out.println("                        ||                   ;'                 /==~- --   -    ---~~~");
        System.out.println("                        ||                (/ ('              /=----         ~~_  --(  '");
        System.out.println("                        ||             ' / ;'             /=----               \\__~");
        System.out.println("     '                ~==_=~          '('             ~-~~      ~~~~        ~~~--\\~'");
        System.out.println("     \\\\                (c_\\_        .i.             /~--    ~~~--   -~     (     '");
        System.out.println("      `\\               (}| /       / : \\           / ~~------~     ~~\\   (");
        System.out.println("      \\ '               ||/ \\      |===|          /~/             ~~~ \\ \\(");
        System.out.println("      ``~\\              ~~\\  )~.~_ >._.< _~-~     |`_          ~~-~     )\\");
        System.out.println("       '-~                 {  /  ) \\___/ (   \\   |` ` _       ~~         '");
        System.out.println("       \\ -~\\                -<__/  -   -  L~ -;   \\\\    \\ _ _/");
        System.out.println("       `` ~~=\\                  {    :    }\\ ,\\    ||   _ :(");
        System.out.println("        \\  ~~=\\__                \\ _/ \\_ /  )  } _//   ( `|'");
        System.out.println("        ``    , ~\\--~=\\           \\     /  / _/ / '    (   '");
        System.out.println("         \\`    } ~ ~~ -~=\\   _~_  / \\ / \\ )^ ( // :_  / '");
        System.out.println("         |    ,          _~-'   '~~__-_  / - |/     \\ (");
        System.out.println("          \\  ,_--_     _/              \\_'---', -~ .   \\");
        System.out.println("           )/      |\\ / |\\   ,~,         \\__ _}     \\_  \"~_");
        System.out.println("           ,      { ( _ )'} ~ - \\_    ~\\  (-:-)       \"\\   ~");
        System.out.println("                  /'' ''  )~ \\~_ ~\\   )->  \\ :|    _,       \"");
        System.out.println("                 (\\  _/)''} | \\~_ ~  /~(   | :)   /          }");
        System.out.println("                <``  >;,,/  )= \\~__ {{{ '  \\ =(  ,   ,       ;");
        System.out.println("               {o_o }_/     |v  '~__  _    )-v|  \"  :       ,\"");
        System.out.println("               {/\"\\_)       {_/'  \\~__ ~\\_ \\\\_} '  {        /~\\");
        System.out.println("               ,/!          '_/    '~__ _-~ \\_' :  '      ,\"  ~");
        System.out.println("              (''`                  /,'~___~    | /     ,\"  \\ ~'");
        System.out.println("             '/, )                 (-)  '~____~\";     ,\"     , }");
        System.out.println("           /,')                    / \\         /  ,~-\"       '~'");
        System.out.println("       (  ''/                     / ( '       /  /          '~'");
        System.out.println("    ~ ~  ,, /) ,                 (/( \\)      ( -)          /~'");
        System.out.println("  (  ~~ )`  ~}                   '  \\)'     _/ /           ~'");
        System.out.println(" { |) /`,--.(  }'                    '     (  /          /~'");
        System.out.println("(` ~ ( c|~~| `}   )                        '/:\\         ,'");
        System.out.println(" ~ )/``) )) '|),                          (/ | \\)");
        System.out.println("  (` (-~(( `~`'  )                        ' (/ '");
        System.out.println("   `~'    )'`')                              '");
        System.out.println("     ` ``");
        System.out.println("");
    }

}

