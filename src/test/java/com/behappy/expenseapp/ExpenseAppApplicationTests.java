package com.behappy.expenseapp;

import com.behappy.expenseapp.domain.Budget;
import com.behappy.expenseapp.repository.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ExpenseAppApplicationTests {
    @Autowired
    public  BudgetRepository budgetRepository;

    @Test
    void contextLoads() {

    }


}
