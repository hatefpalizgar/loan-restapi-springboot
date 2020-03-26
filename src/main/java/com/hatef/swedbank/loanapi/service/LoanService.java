package com.hatef.swedbank.loanapi.service;

import com.hatef.swedbank.loanapi.model.Decision;
import com.hatef.swedbank.loanapi.model.Loan;

import java.util.List;


public interface LoanService {
    
    Loan save(Loan loan);
    Loan findByCustomerId(String customerId);
    Decision approve(String manager, Decision decision);
    List<Loan> findAll();
    Loan update(Loan loan);
}
