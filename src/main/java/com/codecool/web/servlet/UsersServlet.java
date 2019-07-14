package com.codecool.web.servlet;

import com.codecool.web.dao.GameDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseGameDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.Game;
import com.codecool.web.model.User;
import com.codecool.web.service.GameService;
import com.codecool.web.service.UserService;
import com.codecool.web.service.simple.SimpleGameService;
import com.codecool.web.service.simple.SimpleUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/users")
public final class UsersServlet extends AbstractServlet {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            GameDao gameDao = new DatabaseGameDao(connection);
            GameService gameService = new SimpleGameService(gameDao);

            List<Game> purchasedGames = gameDao.findAllPurchasedGameByUserId(userId);

            sendMessage(resp, HttpServletResponse.SC_OK, purchasedGames);
        } catch (SQLException exc) {
            handleSqlError(resp, exc);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);

            User user = (User) request.getSession().getAttribute("user");

            int addToBalance = Integer.parseInt(request.getParameter("balance"));

            userService.addToBalanceById(user.getId(), addToBalance);

            sendMessage(response, HttpServletResponse.SC_OK, "Added to balance");
        } catch (SQLException exc) {
            handleSqlError(response, exc);
        }
    }



}
