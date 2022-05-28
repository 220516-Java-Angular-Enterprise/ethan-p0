package com.revature.fantasyAdventureStore.services;

import com.revature.fantasyAdventureStore.daos.AdvDAO;
import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.models.Store;
import com.revature.fantasyAdventureStore.util.annotations.Inject;
import com.revature.fantasyAdventureStore.util.customExceptions.InvalidUserException;

import java.util.List;

public class UserService {

    @Inject
    private final AdvDAO AdvDAO;

    @Inject
    public UserService(AdvDAO AdvDAO) {
        this.AdvDAO = AdvDAO;
    }

    /*
        Given an AdvName and a password it searches through the database
        to find any matches. If both match then it will return the Adventurer Object.
     */
    public Adventurer login(String advName, String password) {
        /* List<User> users = new ArrayList<>() */
        /* users = userDAO.getAll() */

        Adventurer adv = new Adventurer();
        List<Adventurer> advs = AdvDAO.getAll();

        for (Adventurer u : advs) {
            if (u.getAdvName().equals(advName)) {
                adv.setId(u.getId());
                adv.setAdvName(u.getAdvName());
                adv.setAdvRole(u.getAdvRole());
                adv.setUsrRole(u.getUsrRole());
                adv.setStore_id(u.getStore_id());
                if (u.getPassword().equals(password)) {
                    adv.setPassword(u.getPassword());
                    break;
                }
            }
            if (u.getPassword().equals(password)) {
                adv.setPassword(u.getPassword());
            }
        }

        return isValidCredentials(adv);
    }

    /*
        Adds a new user to the Database
     */
    public void register(Adventurer adv) {
        AdvDAO.save(adv);
    }

    /*
        Runs the AdvName through a Regex to check its complexity
     */
    public boolean isValidAdvName(String advName) {
        if (advName.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")) return true;
        throw new InvalidUserException("Invalid username. Username needs to be 8-20 characters long.");
    }

    /*
        Checks through our database to see if the advName has already been taken by another Adventurer
     */
    public boolean isNotDuplicateUsername(String advName) {
        List<String> advNames = AdvDAO.getAllAdvNames();
        if (advNames.contains(advName)) throw new InvalidUserException("Username is already taken :(");
        return true;
    }

    /*
        Runs the password through a Regex to check its complexity
     */
    public boolean isValidPassword(String password) {
        if(password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) return true;
        throw new InvalidUserException("Invalid username. Username needs to be 8-20 characters long.");
    }


    /*
        Checks the Adventurer Object to see if both the AdvName and the Password are both valid.
     */
    private Adventurer isValidCredentials(Adventurer adv) {
        if (adv.getAdvName() == null && adv.getPassword() == null)
            throw new InvalidUserException("Incorrect username and password.");
        else if (adv.getAdvName() == null) throw new InvalidUserException("Incorrect username.");
        else if (adv.getPassword() == null) throw new InvalidUserException("Incorrect password.");

        return adv;
    }

    public Store getStoreByStoreID (String id) {
        return AdvDAO.getStoreByStoreID(id);
    }

    public boolean updateName(String name, String id) {
        try {
            AdvDAO.updateAdvName(name, id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }
    public boolean updatePassword(String password, String id) {
        try {
            AdvDAO.updatePassword(password, id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }
    public boolean updateUsrRole(String usrRole, String id) {
        try {
            AdvDAO.updateUsrRole(usrRole, id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }
    public boolean updateAdvRole(String advName, String id) {
        try {
            AdvDAO.updateAdvRole(advName, id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }

    public boolean deleteAdv(String id) {
        try {
            AdvDAO.delete(id);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } return false;
    }
}
