package com.behappy.expenseapp.repository;

import com.behappy.expenseapp.domain.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PeriodRepository extends JpaRepository<Period, Long> {
    @Query("SELECT P FROM Period P WHERE P.user.id = ?1")
    public List<Period> getUserPeriods(long userId);

}
