package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.PasswordService;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;

import javax.servlet.ServletOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

public final class SimpleUserService implements UserService {

    private final UserDao userDao;

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() throws SQLException {
        return userDao.findAll();
    }

    @Override
    public User findById(int id) throws SQLException {
        return userDao.findById(id);
    }

    @Override
    public boolean findIfUserExists(String email) throws SQLException {
        return userDao.findIfUserExist(email);
    }

    @Override
    public User addUser(String email, String password, int balance, Role role) throws SQLException {
        return userDao.addUser(email, password, balance, role);
    }

    @Override
    public void addToBalanceById(int id, int deposit) throws SQLException {
        userDao.addToBalanceById(id, deposit);
    }

    @Override
    public User loginUser(String email, String password) throws SQLException, ServiceException {
        PasswordService passwordService = new PasswordService();
        if (email.equals("Guest")){
            return new User(0, "Guest", "Guest", 0, Role.UNREGISTERED);
        }else {
            try{
                User user = userDao.findByEmail(email);
                if(user == null || !passwordService.validatePassword(password, user.getPassword())){
                    throw new ServiceException("Bad login");
                }
                return user;
            }catch (IllegalArgumentException | NoSuchAlgorithmException | InvalidKeySpecException e){
                throw new ServiceException(e.getMessage());
            }
        }
    }
}


