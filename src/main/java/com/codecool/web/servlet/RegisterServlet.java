package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.PersonalInfos;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.PasswordService;
import com.codecool.web.service.PersonalInfosService;
import com.codecool.web.service.UserService;
import com.codecool.web.service.simple.SimplePersonalInfosService;
import com.codecool.web.service.simple.SimpleUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);
            PasswordService passwordService = new PasswordService();



            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Role role = Role.REGISTERED;

            if (!userService.findIfUserExists(email)) {
                User user = userService.addUser(email, passwordService.getHashedPassword(password), 1, role);

                req.setAttribute("user", user);
                sendMessage(resp, HttpServletResponse.SC_OK, user);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

}
