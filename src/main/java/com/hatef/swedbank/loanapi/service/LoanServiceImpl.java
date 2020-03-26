package com.hatef.swedbank.loanapi.service;

import com.hatef.swedbank.loanapi.exception.ResourceNotFoundException;
import com.hatef.swedbank.loanapi.model.Decision;
import com.hatef.swedbank.loanapi.model.Loan;
import com.hatef.swedbank.loanapi.model.LoanStatus;
import com.hatef.swedbank.loanapi.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Validated
@Service
public class LoanServiceImpl implements LoanService {
    
    @Autowired
    LoanRepository loanRepository;
    
    @Override
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }
    
    @Override
    public Loan findByCustomerId(String customerId) {
        Optional<Loan> loanDb = loanRepository.findById(customerId);
        if(loanDb.isPresent()){
            return loanDb.get();
        }else{
            throw new ResourceNotFoundException("Record not found with id : " + customerId);
        }
    }
    
    @Override
    public Decision approve(String manager, Decision decision) {
        return decision.setStatus(LoanStatus.APPROVED)
                .setTimeStamp(LocalDateTime.now());
    }
    
    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }
    
    @Override
    public Loan update(Loan loan) {
        Optional<Loan> loanToUpdate = loanRepository.findById(loan.getCustomerId());
        if(loanToUpdate.isPresent()){
            return getUpdatedLoan(loan, loanToUpdate);
        }else{
            throw new ResourceNotFoundException("Record not found with id : " + loan.getCustomerId());
        }
    }
    
    private Loan getUpdatedLoan(Loan loan, Optional<Loan> loanToUpdate) {
        Loan updatedLoan = loanToUpdate.get()
                .setCustomerId(loan.getCustomerId())
                .setLoanAmount(loan.getLoanAmount())
                .setManagers(loan.getManagers())
                .setStatus(loan.getStatus())
                .setTimeContractSent(loan.getTimeContractSent());
        loanRepository.save(updatedLoan);
        return updatedLoan;
    }
}