package com.codecool.web.servlet;

import com.codecool.web.dao.GameDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseGameDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.Game;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.GameService;
import com.codecool.web.service.PasswordService;
import com.codecool.web.service.UserService;
import com.codecool.web.service.simple.SimpleGameService;
import com.codecool.web.service.simple.SimpleUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/guest")
public final class GuestServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);
            PasswordService passwordService = new PasswordService();

            Role role = Role.UNREGISTERED;

            GameDao gameDao = new DatabaseGameDao(connection);
            GameService gameService = new SimpleGameService(gameDao);

            List<Game> games = gameService.findAll();


            User user = userService.addUser("guest", passwordService.getHashedPassword("guest"), 1, role);

            req.setAttribute("user", user);
            sendMessage(resp, HttpServletResponse.SC_OK, games);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
