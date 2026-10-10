package com.myfinance.platform.service;

import com.myfinance.platform.dto.GoalDTO;
import com.myfinance.platform.jdbc.GoalRepository;
import com.myfinance.platform.model.Goal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GoalService {
    private final GoalRepository repo = new GoalRepository();

    public GoalDTO addGoal(Long userId, String name, String category, BigDecimal targetAmount, LocalDate deadline) {
        Goal goal = new Goal(null, userId, name, category, targetAmount, BigDecimal.ZERO, deadline, null);
        Goal saved = repo.insert(goal);
        return new GoalDTO(
                saved.getId(),
                saved.getUserId(),
                saved.getName(),
                saved.getCategory(),
                saved.getTargetAmount(),
                saved.getCurrentAmount(),
                saved.getDeadline());
    }

    public List<GoalDTO> getGoalsByUserId(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(g -> new GoalDTO(
                        g.getId(),
                        g.getUserId(),
                        g.getName(),
                        g.getCategory(),
                        g.getTargetAmount(),
                        g.getCurrentAmount(),
                        g.getDeadline()))
                .collect(Collectors.toList());
    }

    public void updateCurrentAmount(Long goalId, Long userId, BigDecimal currentAmount) {
        repo.updateCurrentAmount(goalId, userId, currentAmount);
    }

    public void deleteGoal(Long id, Long userId) {
        repo.deleteById(id, userId);
    }
}
