package com.myfinance.platform.servlet;

import com.myfinance.platform.dto.UserDTO;
import com.myfinance.platform.service.auth.AuthenticationService;
import com.myfinance.platform.service.auth.AuthenticationServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final AuthenticationService authService = new AuthenticationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            UserDTO user = authService.login(username, password);
            HttpSession existingSession = req.getSession(false);
            if (existingSession != null) {
                existingSession.invalidate();
            }
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            String destination = "ADMIN".equals(user.getRole()) ? "/admin" : "/dashboard";
            resp.sendRedirect(req.getContextPath() + destination);
        } catch (Exception e) {
            req.setAttribute("error", "Invalid username or password");

            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
