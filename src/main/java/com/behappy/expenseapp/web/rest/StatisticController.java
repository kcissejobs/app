package com.behappy.expenseapp.web.rest;

import com.behappy.expenseapp.domain.Period;
import com.behappy.expenseapp.service.*;
import com.behappy.expenseapp.service.dto.BudgetDTO;
import com.behappy.expenseapp.service.dto.ExpenseDTO;
import com.behappy.expenseapp.service.dto.ExpenseTypeDTO;
import com.behappy.expenseapp.service.dto.PeriodDTO;
import com.behappy.expenseapp.web.rest.vm.BudgetSummaryVM;
import com.behappy.expenseapp.web.rest.vm.PeriodVM;
import com.behappy.expenseapp.web.rest.vm.StatisticVM;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/statistics")
@AllArgsConstructor
@CrossOrigin
public class StatisticController {
    private final BudgetService budgetService;
    private final AccountService accountService;
    private final ExpenseService expenseService;
    private final PeriodService periodService;
    private final ExpenseTypeService expenseTypeService;

    @GetMapping("/accountStatistics/{id}")
    public ResponseEntity<StatisticVM> findStatistics(@PathVariable long id) {
        List<PeriodDTO> openPeriods = periodService.findPeriodOnStatus(id, List.of(Period.Status.OPEN));
        List<BudgetDTO> budgetsOfOpenPeriods = budgetService.findBudgetsOfPeriods(openPeriods);
        double sumBudgetAmount = budgetService.getSumBudgetAmountOfPeriods(id, openPeriods);
        double sumExpenseAmount = expenseService.getSumExpenseAmountOfBudget(id, budgetsOfOpenPeriods);
        Map<Long, List<ExpenseDTO>> groupExpensesByTypeOfBudgets = expenseService.getExpensesByTypeOfBudgets(budgetsOfOpenPeriods);
        Map<String, Double> expenseAmountByTypeData = getExpenseStatisticsData(groupExpensesByTypeOfBudgets, expenseTypeService.findAllExpenseTypes(id));

        Map<PeriodDTO, List<BudgetDTO>> periodBudgetsMap =  budgetService.getFreezeBudgets(id)
               .stream()
               .collect(Collectors.groupingBy(BudgetDTO::getPeriodDTO));

        List<PeriodVM> periodVMList = convertMapToPeriodVMList(id, periodBudgetsMap)
                .stream()
                .sorted(Comparator.comparing(PeriodVM::getEndDate))
                .collect(Collectors.toList());

        StatisticVM statisticVM = StatisticVM.builder()
                .budgetAmount(sumBudgetAmount)
                .expenseAmount(sumExpenseAmount)
                .periodVMList(periodVMList)
                .expenseAmountByType(expenseAmountByTypeData)
                .build();

       return ResponseEntity.ok(statisticVM);
    }

    private List<PeriodVM> convertMapToPeriodVMList(Long accountId, Map<PeriodDTO, List<BudgetDTO>> periodBudgetsMap) {
        List<PeriodVM> periodVMList = new ArrayList<>();
        for (PeriodDTO periodDTO: periodBudgetsMap.keySet()) {
            List<BudgetDTO> periodBudgets = periodBudgetsMap.get(periodDTO);
            List<ExpenseDTO> budgetExpenses = expenseService.findAllAccountExpenses(accountId)
                    .stream()
                    .filter(expenseDTO -> periodBudgets.stream()
                            .anyMatch(
                                    budgetDTO -> budgetDTO.getId() == expenseDTO.getBudgetId()
                            )
                    )
                    .collect(Collectors.toList());

            PeriodDTO periodDTOFounded = periodService.findPeriodById(periodDTO.getId());
            double budgetAmount = periodBudgets.stream()
                    .collect(Collectors.summingDouble(BudgetDTO::getAmount));
            double expenseAmount = budgetExpenses.stream()
                    .collect(Collectors.summingDouble(ExpenseDTO::getAmount));

            periodVMList.add(PeriodVM.builder()
                    .startDate(periodDTOFounded.getStartDate())
                    .endDate(periodDTOFounded.getEndDate())
                    .id(periodDTOFounded.getId())
                    .budgetAmount(budgetAmount)
                    .expenseAmount(expenseAmount)
                    .build()) ;
        }

        return periodVMList;
    }

    private Map<String, Double> getExpenseStatisticsData(
            Map<Long, List<ExpenseDTO>> groupExpensesByTypeOfBudgets,
            List<ExpenseTypeDTO> expenseTypeDTOS
    ) {
        Map<String, Double> expenseStatisticsData = new HashMap<>();
        for(Long idExpenseType: groupExpensesByTypeOfBudgets.keySet()) {
            Optional<ExpenseTypeDTO> optionalExpenseTypeDTO  = expenseTypeDTOS.stream()
                    .filter(expenseTypeDTO -> expenseTypeDTO.getId() == idExpenseType)
                    .findFirst();
            if(optionalExpenseTypeDTO.isPresent()) {
                ExpenseTypeDTO expenseTypeDTO = optionalExpenseTypeDTO.get();
                List<ExpenseDTO> expenseDTOList = groupExpensesByTypeOfBudgets.get(expenseTypeDTO.getId());
                expenseStatisticsData.put(
                        expenseTypeDTO.getDescription(),
                        expenseDTOList.stream().collect(Collectors.summingDouble(expense-> expense.getAmount()))
                );
            }
        }

        return expenseStatisticsData;
    }

    @GetMapping("/summaryBudgets/{accountId}")
    public ResponseEntity<List<BudgetSummaryVM>> findSummaryBudgets(@PathVariable Long accountId) {
       List<BudgetDTO> budgetDTOS = budgetService.getAccountBudgets(accountId);
       List<BudgetSummaryVM> listBudgetSummary = budgetDTOS.stream()
               .map(this::summaryBudget)
               .collect(Collectors.toList());

       return ResponseEntity.ok(listBudgetSummary);
    }


    private BudgetSummaryVM summaryBudget(BudgetDTO budgetDTO) {
        List<ExpenseDTO> expenseDTOs = expenseService.findAllBudgetExpenses(budgetDTO.getId());

        return BudgetSummaryVM.builder()
               .budgetDescription(budgetDTO.getDescription())
               .status(budgetDTO.getStatus())
               .budgetAmount(budgetDTO.getAmount())
               .totalExpenses(totalExpenses(expenseDTOs))
               .build();
    }

    private double totalExpenses(List<ExpenseDTO> expenseDTOs) {
        if(expenseDTOs == null) return 0;
        double sum = expenseDTOs.stream()
                .collect(Collectors.summingDouble(expense-> expense.getAmount()));

        return sum;
    }

}
