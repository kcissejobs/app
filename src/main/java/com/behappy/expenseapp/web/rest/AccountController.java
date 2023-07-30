package com.behappy.expenseapp.web.rest;

import com.behappy.expenseapp.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class AccountController {
    private final AccountService accountService;

    @DeleteMapping("/account/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }



}
