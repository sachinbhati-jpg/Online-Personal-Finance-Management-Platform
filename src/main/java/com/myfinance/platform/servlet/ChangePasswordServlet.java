package com.myfinance.platform.servlet;

import com.myfinance.platform.dto.UserDTO;
import com.myfinance.platform.service.auth.AuthenticationService;
import com.myfinance.platform.service.auth.AuthenticationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangePasswordServlet extends HttpServlet {
    private final AuthenticationService authService = new AuthenticationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getAuthenticatedUser(req, resp) == null) {
            return;
        }

        HttpSession session = req.getSession();
        req.setAttribute("notice", session.getAttribute("passwordNotice"));
        req.setAttribute("error", session.getAttribute("passwordError"));
        session.removeAttribute("passwordNotice");
        session.removeAttribute("passwordError");
        req.getRequestDispatcher("/views/password_change.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDTO user = getAuthenticatedUser(req, resp);
        if (user == null) {
            return;
        }

        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        HttpSession session = req.getSession();
        try {
            if (currentPassword == null || newPassword == null || confirmPassword == null) {
                throw new IllegalArgumentException("Complete all password fields.");
            }
            if (newPassword.length() < 8) {
                throw new IllegalArgumentException("New password must be at least 8 characters.");
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("New passwords do not match.");
            }
            authService.changePassword(user.getUsername(), currentPassword, newPassword);
            session.setAttribute("passwordNotice", "Password changed successfully.");
        } catch (Exception e) {
            session.setAttribute("passwordError", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/password/change");
    }

    private UserDTO getAuthenticatedUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        UserDTO user = session == null ? null : (UserDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return null;
        }
        return user;
    }
}