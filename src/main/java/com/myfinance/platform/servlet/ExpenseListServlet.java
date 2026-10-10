package com.myfinance.platform.servlet;

import com.myfinance.platform.dto.ExpenseDTO;
import com.myfinance.platform.service.ExpenseService;
import com.myfinance.platform.dto.UserDTO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ExpenseListServlet extends HttpServlet {
    private final ExpenseService expenseService = new ExpenseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<ExpenseDTO> expenses = expenseService.getExpensesByUserId(user.getId());
        req.setAttribute("expenses", expenses);
        req.setAttribute("error", session.getAttribute("error"));
        session.removeAttribute("error");
        req.getRequestDispatcher("/views/expense_list.jsp").forward(req, resp);
    }
}