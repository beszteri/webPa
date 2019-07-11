package com.codecool.web.servlet;

import com.codecool.web.dao.PersonalInfosDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabasePersonalInfosDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.PersonalInfos;
import com.codecool.web.model.User;
import com.codecool.web.service.PersonalInfosService;
import com.codecool.web.service.UserService;
import com.codecool.web.service.simple.SimplePersonalInfosService;
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

@WebServlet("/protected/personalInfos")
public class PersonalInfosServlet extends AbstractServlet {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            PersonalInfosDao personalInfosDao = new DatabasePersonalInfosDao(connection);
            PersonalInfosService personalInfosService = new SimplePersonalInfosService(personalInfosDao);

            User user = (User) request.getSession().getAttribute("user");
            int userId = user.getId();

            if(!personalInfosDao.findIfPersonalInfosExist(userId)){
                personalInfosDao.addPersonalInfos("unknown", "unkown", "unkown", userId);
            }
            PersonalInfos personalInfos = personalInfosService.findByUserId(userId);

            sendMessage(response, HttpServletResponse.SC_OK, personalInfos);
        } catch (SQLException exc) {
            handleSqlError(response, exc);
        }
    }



}
