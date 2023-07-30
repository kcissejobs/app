package com.behappy.expenseapp.web.rest;

import com.behappy.expenseapp.service.PeriodService;
import com.behappy.expenseapp.service.dto.PeriodDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/period")
@AllArgsConstructor
@CrossOrigin("*")
public class PeriodController {
    private final PeriodService periodService;

    @PostMapping("/createPeriod")
    public ResponseEntity<PeriodDTO> createPeriod(@Validated @RequestBody PeriodDTO periodDTO) {
        PeriodDTO period = periodService.createPeriod(periodDTO);
        return ResponseEntity.ok(period);
    }

    @PutMapping("/updatePeriod")
    public ResponseEntity<PeriodDTO> updatePeriod(@Validated @RequestBody PeriodDTO periodDTO) {
        PeriodDTO period = periodService.updatePeriod(periodDTO);
        return ResponseEntity.ok(period);
    }

    @DeleteMapping("/deletePeriod/{id}")
    public ResponseEntity deletePeriod(@PathVariable long id) {
        periodService.deleteBudget(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAllUserPeriods/{userId}")
    public ResponseEntity<List<PeriodDTO>> findAllUserPeriods(@PathVariable long userId) {
        List<PeriodDTO> periodDTOs = periodService.getAllUserPeriods(userId);
        return ResponseEntity.ok(periodDTOs);
    }

    @GetMapping("/findPeriod/{id}")
    public ResponseEntity<PeriodDTO> findPeriodById(@PathVariable long id) {
        PeriodDTO periodDTO = periodService.findPeriodById(id);
        if(periodDTO == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(periodDTO);
    }
}
