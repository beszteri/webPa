package com.codecool.web.servlet;

import com.codecool.web.dao.GameDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseGameDao;
import com.codecool.web.dao.database.DatabaseUserDao;
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
import java.rmi.ServerError;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/purchases")
public class PurchaseServlet extends AbstractServlet{

    private final ObjectMapper om = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            GameDao gameDao = new DatabaseGameDao(connection);
            GameService gameService = new SimpleGameService(gameDao);

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);


            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();
            int gameId = Integer.parseInt(req.getParameter("gameBuyId"));
            gameService.buyGame(userId, gameId);


            sendMessage(resp, HttpServletResponse.SC_OK, "Purchase successful");
        } catch (SQLException exc) {
            handleSqlError(resp, exc);
        }catch (ServerError e){
            sendMessage(resp, HttpServletResponse.SC_OK, "Already bought");
        }
    }
}
