package com.myfinance.platform.servlet;

import com.myfinance.platform.dto.UserDTO;
import com.myfinance.platform.service.BudgetService;
import com.myfinance.platform.service.ExpenseService;
import com.myfinance.platform.service.GoalService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setAttribute("user", user);
        req.setAttribute("totalExpenses", new ExpenseService().getTotalExpenseAmount(user.getId()));
        req.setAttribute("budgetCount", new BudgetService().getBudgetsByUserId(user.getId()).size());
        req.setAttribute("goalCount", new GoalService().getGoalsByUserId(user.getId()).size());
        req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
    }
}