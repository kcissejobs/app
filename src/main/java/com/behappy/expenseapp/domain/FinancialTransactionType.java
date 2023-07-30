package com.behappy.expenseapp.domain;

import com.behappy.expenseapp.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BH_FT_TP")
public class FinancialTransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;
    private String description;
    @ManyToOne
    private User user;

    enum TypeOperation {
        DEBIT,
        CREDIT
    }

}
