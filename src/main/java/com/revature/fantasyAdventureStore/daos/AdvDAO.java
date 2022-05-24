package com.revature.fantasyAdventureStore.daos;

import com.revature.fantasyAdventureStore.models.Adventurer;
import com.revature.fantasyAdventureStore.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvDAO implements CrudDAO<Adventurer> {

    // Establishes Database Connection
    Connection con = DatabaseConnection.getCon();


    /*
        Saves a New Adventurer to the Postgresql Database.
        adv: (id, advName, password, advRol, usrRole)
     */
    @Override
    public void save(Adventurer adv) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO adventurers (id, advName, password, advRole, usrRole) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, adv.getId());
            ps.setString(2, adv.getAdvName());
            ps.setString(3, adv.getPassword());
            ps.setString(4, adv.getAdvRole());
            ps.setString(5, adv.getUsrRole());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An Error occurred when trying to save to the database.");
        }
    }

    @Override
    public void update(Adventurer obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Adventurer getById(String id) {
        return null;
    }

    /*
        Returns a List of Adventurers with all their attributes from the Postgresql Database
        adv: (id, advName, password, advRol, usrRole)
        advs = [adv1, adv2, adv3, adv...]
     */
    @Override
    public List<Adventurer> getAll() {
        List<Adventurer> advs = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Adventurer adv = new Adventurer();
                adv.setId(rs.getString("id"));
                adv.setAdvName(rs.getString("advName"));
                adv.setPassword(rs.getString("password"));
                adv.setAdvRole(rs.getString("advRole"));
                adv.setUsrRole(rs.getString("usrRole"));

                advs.add(adv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to get data from to the database.");
        }

        return advs;
    }

    /*
        Returns a List of Adventurers Names (usernames) from the Postgresql Database
        advNames: ["EthanD99", "BDuong12", etc...]
     */
    public List<String> getAllAdvNames() {
        List<String> advNames = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT advName FROM adventurers");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                advNames.add(rs.getString("advName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to get data from to the database.");
        }

        return advNames;
    }


}
