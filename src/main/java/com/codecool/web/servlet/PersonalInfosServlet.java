package com.codecool.web.servlet;

import com.codecool.web.dao.PersonalInfosDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabasePersonalInfosDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.PersonalInfos;
import com.codecool.web.model.User;
import com.codecool.web.service.PasswordService;
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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
                PersonalInfos personalInfos = personalInfosService.findByUserId(userId);
            }
            PersonalInfos personalInfos = personalInfosService.findByUserId(userId);
            sendMessage(response, HttpServletResponse.SC_OK, personalInfos);
        } catch (SQLException exc) {
            handleSqlError(response, exc);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            PersonalInfosDao personalInfosDao = new DatabasePersonalInfosDao(connection);
            PersonalInfosService personalInfosService = new SimplePersonalInfosService(personalInfosDao);
            PasswordService passwordService = new PasswordService();

            User user = (User) req.getSession().getAttribute("user");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String phoneNumber = req.getParameter("phoneNumber");
            System.out.println(address);
            if(address != null && !address.equals("")) {
                personalInfosService.updateAddressById(user.getId(), address);
            }
            if(name != null && !name.equals("")) {
                personalInfosService.updateNameById(user.getId(), name);
            }
            if(phoneNumber != null && !phoneNumber.equals("")) {
                personalInfosService.updatePhoneNumberById(user.getId(), phoneNumber);

            }
            req.getSession().setAttribute("user", user);
            sendMessage(resp, HttpServletResponse.SC_OK, "Your data has been updated.");
        } catch (SQLException exc) {
            handleSqlError(resp, exc);
        }

    }

}
