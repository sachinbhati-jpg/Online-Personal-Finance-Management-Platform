package com.myfinance.platform.servlet;

import com.myfinance.platform.dto.UserDTO;
import com.myfinance.platform.jdbc.BudgetRepository;
import com.myfinance.platform.jdbc.ExpenseRepository;
import com.myfinance.platform.jdbc.GoalRepository;
import com.myfinance.platform.jdbc.UserRepository;
import com.myfinance.platform.model.User;
import com.myfinance.platform.service.auth.AuthenticationService;
import com.myfinance.platform.service.auth.AuthenticationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private final AuthenticationService authService = new AuthenticationServiceImpl();
    private final UserRepository userRepository = new UserRepository();
    private final ExpenseRepository expenseRepository = new ExpenseRepository();
    private final BudgetRepository budgetRepository = new BudgetRepository();
    private final GoalRepository goalRepository = new GoalRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!requireAdmin(req, resp)) {
            return;
        }

        List<User> users = userRepository.findAllForAdmin();
        Map<Long, String> usernamesById = new HashMap<>();
        for (User user : users) {
            usernamesById.put(user.getId(), user.getUsername());
        }

        req.setAttribute("users", users);
        req.setAttribute("usernamesById", usernamesById);
        req.setAttribute("expenses", expenseRepository.findAllForAdmin());
        req.setAttribute("budgets", budgetRepository.findAllForAdmin());
        req.setAttribute("goals", goalRepository.findAllForAdmin());
        HttpSession session = req.getSession();
        req.setAttribute("notice", session.getAttribute("adminNotice"));
        req.setAttribute("error", session.getAttribute("adminError"));
        session.removeAttribute("adminNotice");
        session.removeAttribute("adminError");
        req.getRequestDispatcher("/views/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!requireAdmin(req, resp)) {
            return;
        }

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        try {
            if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty()
                    || password == null || password.isEmpty() || !password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Enter all fields and make sure the passwords match.");
            }
            authService.registerAdmin(username.trim(), email.trim(), password);
            req.getSession().setAttribute("adminNotice", "Admin account created.");
        } catch (Exception e) {
            req.getSession().setAttribute("adminError", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin");
    }

    private boolean requireAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        UserDTO user = session == null ? null : (UserDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        if (!"ADMIN".equals(user.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }
}