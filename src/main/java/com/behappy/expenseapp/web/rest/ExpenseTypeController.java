package com.behappy.expenseapp.web.rest;

import com.behappy.expenseapp.service.ExpenseTypeService;
import com.behappy.expenseapp.service.dto.ExpenseTypeDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/expenseType")
@CrossOrigin
public class ExpenseTypeController {
    private final ExpenseTypeService expenseTypeService;

    @PostMapping("/createExpenseType")
    public ResponseEntity<ExpenseTypeDTO> createExpenseType(@RequestBody @Validated ExpenseTypeDTO expenseTypeDTO) {
        return ResponseEntity.ok(expenseTypeService.createExpenseType(expenseTypeDTO));
    }

    @PutMapping("/updateExpenseType")
    public ResponseEntity<ExpenseTypeDTO> updateExpenseType(@RequestBody @Validated ExpenseTypeDTO expenseTypeDTO) {
        return ResponseEntity.ok(expenseTypeService.updateExpenseType(expenseTypeDTO));
    }

    @GetMapping("/findAllExpenseTypes/{id}")
    public ResponseEntity<List<ExpenseTypeDTO>> finnAllExpenseTypes(@PathVariable Long id) {
        return ResponseEntity.ok(expenseTypeService.findAllExpenseTypes(id));
    }

    @DeleteMapping("/deleteExpenseType/{id}")
    public ResponseEntity deleteExpenseType(@PathVariable Long id) {

        expenseTypeService.deleteExpenseType(id);
        return ResponseEntity.ok().build();
    }
}
