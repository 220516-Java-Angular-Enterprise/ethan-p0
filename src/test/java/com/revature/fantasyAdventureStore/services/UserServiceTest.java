package com.revature.fantasyAdventureStore.services;

import com.revature.fantasyAdventureStore.daos.AdvDAO;
import com.revature.fantasyAdventureStore.models.Adventurer;
import org.junit.jupiter.api.Test;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidUserException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService usr = new UserService(new AdvDAO());

    /*
        UserServiceTest:

        This test file is used to confirm the validation of the username and password functions.
        It checks to make sure the Regex for both the advName and password work correctly.

        Because the UserService will only throw an exception if it's not valid then we have to use
        and assertThrows instead of assertEquals.
     */


    @Test
    void isValidAdvName() {
        assertEquals(true, usr.isValidAdvName("RoyalShield"));
        assertThrows( InvalidUserException.class, () -> {usr.isValidAdvName("Royal:Shield"); });
        assertEquals(true, usr.isValidAdvName("alecGuarino123"));
        assertThrows( InvalidUserException.class, () -> {usr.isValidAdvName("Bob"); });
        assertThrows( InvalidUserException.class, () -> {usr.isValidAdvName("Supercalifragilisticexpialidocious"); });
        assertEquals(true, usr.isValidAdvName("RoyalShield.9"));
    }

    @Test
    void isValidPassword() {
        assertEquals(true, usr.isValidPassword("P@ssw0rd"));
        assertEquals(true, usr.isValidPassword("R0y@alShield"));
        assertThrows( InvalidUserException.class, () -> {usr.isValidPassword("hello"); });
        assertThrows( InvalidUserException.class, () -> {usr.isValidPassword("password"); });
        assertThrows( InvalidUserException.class, () -> {usr.isValidPassword("P@ssword"); });
        assertThrows( InvalidUserException.class, () -> {usr.isValidPassword("Passw0rd"); });
    }

    @Test
    void isNotDuplicateUsername() {
        assertEquals(true, usr.isNotDuplicateUsername("BDound9"));
        assertThrows(InvalidUserException.class, () -> {usr.isNotDuplicateUsername("RoyalShield"); });
    }

    @Test
    void getStoreIdFromAdvRole() {
        assertEquals("2", usr.getStoreIdFromAdvRole("Barbarian"));
        assertEquals("2", usr.getStoreIdFromAdvRole("Ranger"));
        assertEquals("2", usr.getStoreIdFromAdvRole("Paladin"));
        assertEquals("2", usr.getStoreIdFromAdvRole("Fighter"));
        assertEquals("2", usr.getStoreIdFromAdvRole("Cleric"));
        assertEquals("2", usr.getStoreIdFromAdvRole("Monk"));

        assertEquals("5", usr.getStoreIdFromAdvRole("Bard"));

        assertEquals("1", usr.getStoreIdFromAdvRole("Druid"));

        assertEquals("3", usr.getStoreIdFromAdvRole("Rogue"));

        assertEquals("0", usr.getStoreIdFromAdvRole("Wizard"));
        assertEquals("0", usr.getStoreIdFromAdvRole("Warlock"));
        assertEquals("0", usr.getStoreIdFromAdvRole("Wizard"));

        assertEquals("4", usr.getStoreIdFromAdvRole("Test"));

    }

    @Test
    void updateUsrRole() {
        usr.deleteAdv("0");
        Adventurer adv = new Adventurer();
        adv.setId("0"); adv.setAdvName("TestName"); adv.setPassword("TestPass1@");
        adv.setStore_id("0"); adv.setUsrRole("DEFAULT"); adv.setAdvRole("Mage");
        usr.register(adv);

        assertEquals(true, usr.updateUsrRole("ADMIN", adv.getId()));
        assertEquals(true, usr.updateUsrRole("DEFAULT", adv.getId()));
    }

}