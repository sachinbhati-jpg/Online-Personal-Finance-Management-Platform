package com.myfinance.platform.service;

import com.myfinance.platform.dto.ExpenseDTO;
import com.myfinance.platform.jdbc.BudgetRepository;
import com.myfinance.platform.jdbc.ExpenseRepository;
import com.myfinance.platform.model.Expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseService {

        private final ExpenseRepository repo = new ExpenseRepository();

        /**
         * Adds a new expense for the given user.
         *
         * @param userId      The ID of the user owning the expense.
         * @param date        The date of the expense.
         * @param category    The expense category (e.g., Food, Transport).
         * @param amount      The expense amount.
         * @param description Optional description.
         * @return The saved expense as a DTO.
         */
        public ExpenseDTO addExpense(Long userId, LocalDate date, String category, BigDecimal amount,
                        String description) {
                Expense expense = new Expense(null, userId, date, category, amount, description, LocalDateTime.now());
                Expense saved = repo.insert(expense);
                BudgetRepository budgetRepo = new BudgetRepository();
                budgetRepo.findByUserId(userId).stream()
                                .filter(b -> b.getCategory().equalsIgnoreCase(category))
                                .forEach(b -> budgetRepo.updateSpentAmount(b.getId(), userId,
                                                b.getSpentAmount().add(amount)));
                return new ExpenseDTO(
                                saved.getId(),
                                saved.getUserId(),
                                saved.getDate(),
                                saved.getCategory(),
                                saved.getAmount(),
                                saved.getDescription());
        }

        /**
         * Retrieves all expenses for a specific user, ordered by date (newest first).
         *
         * @param userId The ID of the user.
         * @return A list of expense DTOs.
         */
        public List<ExpenseDTO> getExpensesByUserId(Long userId) {
                return repo.findByUserId(userId).stream()
                                .map(e -> new ExpenseDTO(
                                                e.getId(),
                                                e.getUserId(),
                                                e.getDate(),
                                                e.getCategory(),
                                                e.getAmount(),
                                                e.getDescription()))
                                .collect(Collectors.toList());
        }

        /**
         * Deletes an expense by its ID.
         *
         * @param id The ID of the expense to delete.
         */
        public void deleteExpense(Long id, Long userId) {
                repo.deleteById(id, userId);
        }

        /**
         * Updates an existing expense.
         *
         * @param id          The ID of the expense to update.
         * @param date        The new date.
         * @param category    The new category.
         * @param amount      The new amount.
         * @param description The new description.
         */
        public void updateExpense(Long id, Long userId, LocalDate date, String category, BigDecimal amount,
                        String description) {
                // Fetch the existing expense to preserve the user_id and created_at if needed
                Expense existing = repo.findById(id, userId)
                                .orElseThrow(() -> new IllegalArgumentException("Expense not found with id: " + id));

                Expense updated = new Expense(
                                id,
                                existing.getUserId(),
                                date,
                                category,
                                amount,
                                description,
                                existing.getCreatedAt() // keep original timestamp or reset to now if preferred
                );
                repo.update(updated, userId);
        }

        /**
         * Calculates the total amount of expenses for a user.
         *
         * @param userId The ID of the user.
         * @return The sum of all expense amounts.
         */
        public BigDecimal getTotalExpenseAmount(Long userId) {
                return repo.findByUserId(userId).stream()
                                .map(Expense::getAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        /**
         * Groups expenses by category and returns the total per category.
         *
         * @param userId The ID of the user.
         * @return A list of objects containing category and total amount.
         */
        public List<Object[]> getExpensesByCategory(Long userId) {
                // This would typically be done via a native SQL query in the repository.
                // For simplicity, we compute it in Java (acceptable for small datasets).
                return repo.findByUserId(userId).stream()
                                .collect(Collectors.groupingBy(
                                                Expense::getCategory,
                                                Collectors.reducing(BigDecimal.ZERO, Expense::getAmount,
                                                                BigDecimal::add)))
                                .entrySet()
                                .stream()
                                .map(e -> new Object[] { e.getKey(), e.getValue() })
                                .collect(Collectors.toList());
        }
}