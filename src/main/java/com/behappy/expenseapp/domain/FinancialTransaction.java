package com.behappy.expenseapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private STATUS status;
    @ManyToOne
    private FinancialTransactionType financialTransactionType;

    @ManyToOne
    private Account account;
    @ManyToOne
    private Budget budget;
    @OneToMany(mappedBy = "financialTransaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<FinancialTransactionDetails> financialTransactionDetails;


    enum STATUS {
        FREEZE,
        CANCEL
    }
}
