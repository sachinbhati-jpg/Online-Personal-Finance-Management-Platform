package com.myfinance.platform.servlet;

import com.myfinance.platform.dto.BudgetDTO;
import com.myfinance.platform.dto.UserDTO;
import com.myfinance.platform.service.BudgetService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BudgetListServlet extends HttpServlet {
    private final BudgetService budgetService = new BudgetService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        List<BudgetDTO> budgets = budgetService.getBudgetsByUserId(user.getId());
        req.setAttribute("budgets", budgets);
        HttpSession session = req.getSession(false);
        if (session != null) {
            req.setAttribute("error", session.getAttribute("budgetError"));
            session.removeAttribute("budgetError");
        }
        req.getRequestDispatcher("/views/budget_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String category = req.getParameter("category");
        String period = req.getParameter("period");
        try {
            BigDecimal limitAmount = new BigDecimal(req.getParameter("limitAmount"));
            if (category == null || category.trim().isEmpty() ||
                    !("MONTHLY".equals(period) || "YEARLY".equals(period)) || limitAmount.signum() <= 0) {
                req.getSession().setAttribute("budgetError", "Enter a category, period, and positive limit.");
            } else {
                budgetService.addBudget(user.getId(), category.trim(), period, limitAmount);
            }
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("budgetError", "Enter a valid budget limit.");
        } catch (RuntimeException e) {
            req.getSession().setAttribute("budgetError",
                    "Could not save the budget. Check for a duplicate category and period.");
        }
        resp.sendRedirect(req.getContextPath() + "/budget/list");
    }
}