package com.myfinance.platform.servlet;

import com.myfinance.platform.service.auth.AuthenticationService;
import com.myfinance.platform.service.auth.AuthenticationServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private final AuthenticationService authService = new AuthenticationServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (password == null || !password.equals(req.getParameter("confirmPassword"))) {
            req.setAttribute("error", "Passwords do not match.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        try {
            authService.register(username, email, password);
            req.setAttribute("message", "Registration successful! Please login.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }
}