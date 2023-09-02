package com.behappy.expenseapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BH_EXPENSE")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private double amount;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate creationDate;

    @ManyToOne
    private ExpenseType expenseType;

    @ManyToOne
    private Budget budget;

    public enum Status {
        PENDING,
        FREEZE,
        CANCEL
    }

    public boolean isPending() {
        return this.status.equals(Status.PENDING);
    }

    public boolean isFreeze() {
        return this.status.equals(Status.FREEZE);
    }

    public boolean isCanceled() {
        return this.status.equals(Status.CANCEL);
    }
}
