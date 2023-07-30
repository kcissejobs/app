package com.behappy.expenseapp.web.rest;

import com.behappy.expenseapp.service.BudgetService;
import com.behappy.expenseapp.service.dto.BudgetDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/budget")
@AllArgsConstructor
@CrossOrigin("*")
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping("/accountBudgets/{accountId}")
    public ResponseEntity<List<BudgetDTO>> getAccountBudgets(@PathVariable long accountId) {
        List<BudgetDTO> budgetDTOS = budgetService.getAccountBudgets(accountId);
        return ResponseEntity.ok(budgetDTOS);
    }

    @PostMapping("/createBudget")
    public ResponseEntity createBudget(@Validated @RequestBody BudgetDTO budgetDTO) {
        BudgetDTO createdBudgetDTO = budgetService.createBudget(budgetDTO);
        return ResponseEntity.ok(createdBudgetDTO);
    }

    @PutMapping("/updateBudget")
    public ResponseEntity updateBudget(@Validated @RequestBody BudgetDTO budgetDTO) {
        BudgetDTO createdBudgetDTO = budgetService.updateBudget(budgetDTO);
        return ResponseEntity.ok(createdBudgetDTO);
    }

    @DeleteMapping("/deleteBudget/{id}")
    public ResponseEntity deleteBudget(@PathVariable long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findBudgetById/{id}")
    public ResponseEntity<BudgetDTO> findBudgetById(@PathVariable long id) {
        BudgetDTO budgetDTO = budgetService.findBudgetById(id);
        return ResponseEntity.ok(budgetDTO);
    }

    @GetMapping("/findAllBudgets")
    public ResponseEntity<List<BudgetDTO>> findAllBudgets() {
        List<BudgetDTO> budgetDTOs = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgetDTOs);
    }

}
