package com.revature.fantasyAdventureStore.services;

import org.junit.jupiter.api.Test;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidUserException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService usr = new UserService();

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
}