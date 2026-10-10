package com.myfinance.platform.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Expense {
    private Long id;
    private Long userId;
    private LocalDate date;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;

    public Expense() {
    }

    public Expense(Long id, Long userId, LocalDate date, String category, BigDecimal amount, String description,
            LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}