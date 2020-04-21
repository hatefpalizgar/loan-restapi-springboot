package com.hatef.swedbank.loanapi.model;

import com.hatef.swedbank.loanapi.validation.CustomerIdConstraint;
import com.hatef.swedbank.loanapi.validation.ManagerNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Loan {
    
    @CustomerIdConstraint
    private String customerId;
    
    private Double loanAmount;
    
    @ManagerNumberConstraint
    private List<String> managers;
    
    @Nullable
    private LoanStatus status;
    
    @Nullable
    private LocalDateTime timeContractSent;
    
    private Map<String, Decision> decisionMap = new HashMap<>();
}