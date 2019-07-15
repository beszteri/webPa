package com.codecool.web.servlet;

import com.codecool.web.dao.GameDao;
import com.codecool.web.dao.database.DatabaseGameDao;
import com.codecool.web.model.Game;
import com.codecool.web.service.GameService;
import com.codecool.web.service.simple.SimpleGameService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/games")
public class GameServlet extends AbstractServlet{

    private final ObjectMapper om = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            GameDao gameDao = new DatabaseGameDao(connection);
            GameService gameService = new SimpleGameService(gameDao);

            List<Game> games = gameService.findAll();

            sendMessage(resp, HttpServletResponse.SC_OK, games);
        }catch (SQLException e){
            handleSqlError(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            GameDao gameDao = new DatabaseGameDao(connection);
            GameService gameService = new SimpleGameService(gameDao);

            String name = req.getParameter("name");
            String platform = req.getParameter("platform");
            String imageUrl = req.getParameter("imageUrl");
            int price = Integer.parseInt(req.getParameter("price"));

            gameService.saveGame(name, platform, imageUrl, price);

            sendMessage(resp, HttpServletResponse.SC_OK, "Game saved");

        }catch (SQLException e){
            handleSqlError(resp, e);
        }
    }
}
