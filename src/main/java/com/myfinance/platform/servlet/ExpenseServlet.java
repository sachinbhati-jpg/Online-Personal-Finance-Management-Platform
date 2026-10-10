package com.myfinance.platform.servlet;

import com.myfinance.platform.dto.UserDTO;
import com.myfinance.platform.service.ExpenseService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ExpenseServlet extends HttpServlet {
    private final ExpenseService expenseService = new ExpenseService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));
                expenseService.deleteExpense(id, user.getId());
            } else if ("update".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));
                LocalDate date = LocalDate.parse(req.getParameter("date"));
                BigDecimal amount = new BigDecimal(req.getParameter("amount"));
                expenseService.updateExpense(id, user.getId(), date, req.getParameter("category"),
                        amount, req.getParameter("description"));
            } else { // create
                LocalDate date = LocalDate.parse(req.getParameter("date"));
                BigDecimal amount = new BigDecimal(req.getParameter("amount"));
                expenseService.addExpense(user.getId(), date, req.getParameter("category"),
                        amount, req.getParameter("description"));
            }
        } catch (DateTimeParseException | NumberFormatException e) {
            session.setAttribute("error", "Invalid input. Check date (YYYY-MM-DD) and amount.");
        }
        resp.sendRedirect(req.getContextPath() + "/expense/list");
    }
}