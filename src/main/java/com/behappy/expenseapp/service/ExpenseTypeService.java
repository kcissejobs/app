package com.behappy.expenseapp.service;

import com.behappy.expenseapp.domain.ExpenseType;
import com.behappy.expenseapp.exception.ExpenseTypeNotFoundException;
import com.behappy.expenseapp.repository.ExpenseTypeRepository;
import com.behappy.expenseapp.service.dto.ExpenseTypeDTO;
import com.behappy.expenseapp.service.mapper.ExpenseTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class ExpenseTypeService {
    private final ExpenseTypeRepository expenseTypeRepository;

    /**
     * Create expense type
     * @param expenseTypeDTO
     * @return created ExpenseTypeDTO
     */
    public ExpenseTypeDTO createExpenseType(ExpenseTypeDTO expenseTypeDTO) {
        ExpenseType expenseType = ExpenseTypeMapper.toEntity(expenseTypeDTO);
        expenseType = expenseTypeRepository.save(expenseType);
        return ExpenseTypeMapper.toDTO(expenseType);
    }

    /**
     * Find all user expense type
     * @param userId
     * @return list of expense type List<ExpenseTypeDTO>
     */
    public List<ExpenseTypeDTO> findAllExpenseTypes(long userId) {
        List<ExpenseType> expenseTypes = expenseTypeRepository.findAllExpenses(userId);
        if(expenseTypes == null ) return List.of();

        return ExpenseTypeMapper.toDTOs(expenseTypes);
    }

    /**
     * Update an expense type
     * @param expenseTypeDTO
     * @return the updated expense type
     */
    public ExpenseTypeDTO updateExpenseType(ExpenseTypeDTO expenseTypeDTO) {
        ExpenseType expenseType = ExpenseTypeMapper.toEntity(expenseTypeDTO);
        if(expenseType == null || expenseType.getId() == null) {
            throw new ExpenseTypeNotFoundException();
        }

        expenseType = expenseTypeRepository.save(expenseType);
        return ExpenseTypeMapper.toDTO(expenseType);
    }


    public void deleteExpenseType(Long id) {
        Optional<ExpenseType> expenseTypeOptional = expenseTypeRepository.findById(id);
        if(!expenseTypeOptional.isPresent()) {
            throw new ExpenseTypeNotFoundException();
        }

        expenseTypeRepository.delete(expenseTypeOptional.get());
    }
}
