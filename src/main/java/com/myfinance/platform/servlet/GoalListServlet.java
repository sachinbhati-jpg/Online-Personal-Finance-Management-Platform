package com.myfinance.platform.servlet;

import com.myfinance.platform.dto.GoalDTO;
import com.myfinance.platform.dto.UserDTO;
import com.myfinance.platform.service.GoalService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class GoalListServlet extends HttpServlet {
    private final GoalService goalService = new GoalService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        List<GoalDTO> goals = goalService.getGoalsByUserId(user.getId());
        req.setAttribute("goals", goals);
        HttpSession session = req.getSession(false);
        if (session != null) {
            req.setAttribute("error", session.getAttribute("goalError"));
            session.removeAttribute("goalError");
        }
        req.getRequestDispatcher("/views/goal_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String name = req.getParameter("name");
        String category = req.getParameter("category");
        try {
            BigDecimal targetAmount = new BigDecimal(req.getParameter("targetAmount"));
            LocalDate deadline = LocalDate.parse(req.getParameter("deadline"));
            if (name == null || name.trim().isEmpty() || targetAmount.signum() <= 0) {
                req.getSession().setAttribute("goalError", "Enter a name and a positive target amount.");
            } else {
                goalService.addGoal(user.getId(), name.trim(), category, targetAmount, deadline);
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            req.getSession().setAttribute("goalError", "Enter a valid target amount and deadline.");
        } catch (RuntimeException e) {
            req.getSession().setAttribute("goalError",
                    "Could not save the goal. Check the entered values and database connection.");
        }
        resp.sendRedirect(req.getContextPath() + "/goal/list");
    }
}