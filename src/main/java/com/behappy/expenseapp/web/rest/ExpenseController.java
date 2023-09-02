package com.behappy.expenseapp.web.rest;

import com.behappy.expenseapp.service.ExpenseService;
import com.behappy.expenseapp.service.dto.ExpenseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/expense")
@AllArgsConstructor
@CrossOrigin
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/createExpense")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody @Validated ExpenseDTO expenseDTO) {
       return ResponseEntity.ok(expenseService.createExpense(expenseDTO));
    }

    @PutMapping ("/updateExpense")
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestBody @Validated ExpenseDTO expenseDTO) {
        return ResponseEntity.ok(expenseService.updateExpense(expenseDTO));
    }

    @DeleteMapping ("/deleteExpenseById/{id}")
    public ResponseEntity deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAccountExpenses/{id}")
    public ResponseEntity<List<ExpenseDTO>> findAllAccountExpenses(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.findAllAccountExpenses(id));
    }

    @GetMapping("/findExpenseById/{id}")
    public ResponseEntity<ExpenseDTO> findExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.findExpenseById(id));
    }

}