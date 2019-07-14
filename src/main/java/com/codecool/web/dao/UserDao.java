package com.codecool.web.dao;

import com.codecool.web.model.Role;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    User findByEmail(String email) throws SQLException;

    User findById(int id) throws SQLException;

    List<User> findAll() throws SQLException;

    boolean findIfUserExist(String email) throws SQLException;

    User addUser(String email, String password, int balance, Role role) throws SQLException;

    void addToBalanceById(int id, int deposit) throws SQLException;


}
