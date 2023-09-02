package com.behappy.expenseapp.domain;

import com.behappy.expenseapp.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BH_PERIOD")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "period", fetch = FetchType.EAGER)
    private List<Budget> budgets;
    @ManyToOne
    private User user;

    public enum Status {
        PENDING,
        OPEN,
        CLOSE
    }

}
