package com.codecool.web.service;

import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    List<User> findAll() throws SQLException;

    User findById(int id) throws SQLException;

    boolean findIfUserExists(String email) throws SQLException;

    User addUser(String email, String password, int balance, Role role) throws SQLException;

    void addToBalanceById(int id, int deposit) throws SQLException;

    User loginUser(String email, String password) throws SQLException, ServiceException;

}
