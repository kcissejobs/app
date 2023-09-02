package com.behappy.expenseapp.domain;

import com.behappy.expenseapp.security.user.User;
import jakarta.persistence.*;
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
@Table(name = "BH_EXPENSE_TYPE")
public class ExpenseType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "expenseType", fetch = FetchType.EAGER)
    private List<Expense> expenses;

}
