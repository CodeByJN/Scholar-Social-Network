package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationController extends HttpServlet {
    private final UserDAO userDAO;

    public UserAuthenticationController() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            req.setAttribute("message", "Invalid email or password.");
            req.setAttribute("messageColor", "red");
        } else {
            User user = userDAO.getUserByEmail(email);
            if (user == null || !user.getPassword().equals(password)) {
                req.setAttribute("message", "Authentication failed.");
                req.setAttribute("messageColor", "red");
            } else {
                req.setAttribute("message", "Login successful!");
                req.setAttribute("messageColor", "green");
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/Login.jsp");
        dispatcher.forward(req, resp);
    }
}
