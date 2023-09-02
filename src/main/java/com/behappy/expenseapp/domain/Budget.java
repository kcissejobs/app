package com.behappy.expenseapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BH_BUDGET")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private double amount;
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @ManyToOne
    private Account account;

    @NotNull
    @ManyToOne
    private Period period;

    @OneToMany(mappedBy = "budget",  fetch = FetchType.LAZY)
    private List<Expense> expenses;

    public boolean isPending() {
        return this.status.equals(Status.PENDING);
    }

    public boolean isFreeze() {
        return this.status.equals(Status.FREEZE);
    }

    public boolean isCanceled() {
        return this.status.equals(Status.CANCEL);
    }

    public enum Status {
        PENDING,
        FREEZE,
        CANCEL
    }
}
