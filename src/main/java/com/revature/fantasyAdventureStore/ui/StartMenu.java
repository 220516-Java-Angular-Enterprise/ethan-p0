package com.revature.fantasyAdventureStore.ui;

import java.util.Scanner;

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
        // Enter a username and a password. Make sure they are not already taken and
        // make sure they meet the requirments for the username and password.
        String username;
        String password;
        Scanner scan = new Scanner(System.in);

        completeSignup: {
            // Loop Through Sign-up until successful account creation
            // Ask the user for a username
            // Check to make sure the username is a valid username and has not been taken
            // (Don't know how to do that last part yet)
            //
            // Ask the user for a Password
            // Repeat above steps
            //
            // Ask user to confirm account credentials
            //
            //
            while (true) {
                //Ask the user to create a username
                System.out.print("\nUsername: ");
                username = scan.nextLine();

                //




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

