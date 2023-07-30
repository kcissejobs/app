package com.behappy.expenseapp.service;

import com.behappy.expenseapp.domain.Period;
import com.behappy.expenseapp.repository.PeriodRepository;
import com.behappy.expenseapp.service.dto.PeriodDTO;
import com.behappy.expenseapp.service.mapper.PeriodMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class PeriodService {
    private final PeriodRepository periodRepository;
    private final Logger log = LoggerFactory.getLogger(PeriodService.class);

    /**
     * Get All periods
     * @return list of PeriodDTO
     */
    public List<PeriodDTO> getAllPeriods() {
        log.debug("Request to all periods");
        List<Period> periods = periodRepository.findAll();
        return PeriodMapper.toDTOs(periods);
    }
    /**
     * Get All user periods
     * @param userId
     * @return list of PeriodDTO
     */
    public List<PeriodDTO> getAllUserPeriods(long userId) {
        log.debug("Request to retrieve all period of user {} ", userId);
        List<Period> periods = periodRepository.getUserPeriods(userId);
        return PeriodMapper.toDTOs(periods);
    }

    /**
     * Create a new Period
     * @param periodDTO
     * @return Period
     */
    public PeriodDTO createPeriod(PeriodDTO periodDTO) {
        log.debug("Request to create the period {} ", periodDTO);
        Period period = PeriodMapper.toEntity(periodDTO);
        period = periodRepository.save(period);
        return PeriodMapper.toDTO(period);
    }

    /**
     * Update a period
     * @param periodDTO
     * @return updated PeriodDTO
     */
    public PeriodDTO updatePeriod(PeriodDTO periodDTO) {
        log.debug("Request to create the period {} ", periodDTO);
        Optional<Period> optionalPeriod = periodRepository.findById(periodDTO.getId());
        if(!optionalPeriod.isPresent()) {
            throw new PeriodNotFoundException();
        }

        Period period = optionalPeriod.get();
        period.setStartDate(periodDTO.getStartDate());
        period.setEndDate(periodDTO.getEndDate());
        period = periodRepository.save(period);

        return PeriodMapper.toDTO(period);
    }

    /**
     * Delete a Period
     * @param periodId
     */
    public void deleteBudget(Long periodId) {
        log.debug("Request to delete Period with ID {}", periodId);
        periodRepository.findById(periodId)
                .ifPresentOrElse(period -> {periodRepository.delete(period);}, ()-> new PeriodNotFoundException());
    }

    /**
     * Retrieve a period
     * @param periodId
     * @return the PeriodDTO or Null
     */
    public PeriodDTO findPeriodById(Long periodId) {
        log.debug("Request to find Period by ID {}", periodId);
        Optional<Period> optionalPeriod = periodRepository.findById(periodId);
        if(optionalPeriod.isEmpty()) return null;

        return PeriodMapper.toDTO(optionalPeriod.get());
    }

}
