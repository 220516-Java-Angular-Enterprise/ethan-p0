package com.revature.fantasyAdventureStore.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService usr = new UserService();

    @Test
    void isValidAdvName() {
        assertEquals(true, usr.isValidAdvName("RoyalShield"));
        assertEquals(false, usr.isValidAdvName("Royal:Shield"));
        assertEquals(true, usr.isValidAdvName("alecGuarino123"));
        assertEquals(false, usr.isValidAdvName("Bob"));
        assertEquals(false, usr.isValidAdvName("Supercalifragilisticexpialidocious"));
        assertEquals(true, usr.isValidAdvName("RoyalShield.9"));
    }

    @Test
    void isValidPassword() {
        assertEquals(true, usr.isValidPassword("P@ssw0rd"));
        assertEquals(true, usr.isValidPassword("R0y@alShield"));
        assertEquals(false, usr.isValidPassword("hello"));
        assertEquals(false, usr.isValidPassword("password"));
        assertEquals(false, usr.isValidPassword("P@ssword"));
        assertEquals(false, usr.isValidPassword("Passw0rd"));
    }
}