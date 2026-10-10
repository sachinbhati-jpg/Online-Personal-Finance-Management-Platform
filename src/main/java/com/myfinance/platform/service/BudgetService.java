package com.myfinance.platform.service;

import com.myfinance.platform.dto.BudgetDTO;
import com.myfinance.platform.jdbc.BudgetRepository;
import com.myfinance.platform.model.Budget;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetService {
    private final BudgetRepository repo = new BudgetRepository();

    public BudgetDTO addBudget(Long userId, String category, String period, BigDecimal limitAmount) {
        Budget budget = new Budget(null, userId, category, period, limitAmount, BigDecimal.ZERO, null);
        Budget saved = repo.insert(budget);
        return new BudgetDTO(
                saved.getId(),
                saved.getUserId(),
                saved.getCategory(),
                saved.getPeriod(),
                saved.getLimitAmount(),
                saved.getSpentAmount());
    }

    public List<BudgetDTO> getBudgetsByUserId(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(b -> new BudgetDTO(
                        b.getId(),
                        b.getUserId(),
                        b.getCategory(),
                        b.getPeriod(),
                        b.getLimitAmount(),
                        b.getSpentAmount()))
                .collect(Collectors.toList());
    }

    public void updateSpentAmount(Long budgetId, Long userId, BigDecimal spentAmount) {
        repo.updateSpentAmount(budgetId, userId, spentAmount);
    }

    public void deleteBudget(Long id, Long userId) {
        repo.deleteById(id, userId);
    }
}
