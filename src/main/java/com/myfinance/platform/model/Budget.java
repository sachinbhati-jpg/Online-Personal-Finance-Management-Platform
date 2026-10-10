package com.myfinance.platform.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Budget {
    private Long id;
    private Long userId;
    private String category;
    private String period; // e.g., MONTHLY, WEEKLY
    private BigDecimal limitAmount;
    private BigDecimal spentAmount;
    private LocalDateTime createdAt;

    public Budget() {
    }

    public Budget(Long id, Long userId, String category, String period, BigDecimal limitAmount, BigDecimal spentAmount,
            LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.period = period;
        this.limitAmount = limitAmount;
        this.spentAmount = spentAmount;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(BigDecimal spentAmount) {
        this.spentAmount = spentAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}