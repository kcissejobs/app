package com.behappy.expenseapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BH_FT")
public class FinancialTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double amount;
    private LocalDate localDateTime = LocalDate.now();
   // @Enumerated(EnumType.STRING)
   // private Status status;
    @NotNull
    private Long parentId;
    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    private Account account;

    /*
    public enum Status {
        FREEZE,
        CANCEL
    }*/
    public enum TypeOperation {
        DEBIT,
        CREDIT
    }

    public enum Type {
        BP,
        BX,
        EP,
        EX;
    }

    public boolean isBudget() {
        return this.type.equals(Type.BP);
    }

    public boolean isCanceledBudget() {
        return this.type.equals(Type.BX);
    }

    public boolean isExpense() {
        return this.type.equals(Type.EP);
    }

    public boolean isCanceledExpense() {
        return this.type.equals(Type.EX);
    }

    public boolean isDebit() {
        return this.typeOperation.equals(TypeOperation.DEBIT);
    }
    public boolean isCredit() {
        return this.typeOperation.equals(TypeOperation.CREDIT);
    }

}
