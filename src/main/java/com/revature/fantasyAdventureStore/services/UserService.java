package com.revature.fantasyAdventureStore.services;

import com.revature.fantasyAdventureStore.models.AdventurerName;
import com.revature.fantasyAdventureStore.util.annotations.Inject;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidUserException;

import java.util.List;
public class UserService {



    public boolean isValidAdvName(String advName) {
        if (advName.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")) return true;
        throw new InvalidUserException("Invalid username. Username needs to be 8-20 characters long.");
    }

    public boolean isValidPassword(String password) {
        if(password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) return true;
        throw new InvalidUserException("Invalid username. Username needs to be 8-20 characters long.");
    }

}
