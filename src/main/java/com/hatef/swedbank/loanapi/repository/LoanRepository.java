package com.hatef.swedbank.loanapi.repository;

import com.hatef.swedbank.loanapi.model.Loan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class LoanRepository implements GenericRepository<Loan> {
    
    List<Loan> repository = new ArrayList<>();
    
    @Override
    public Loan save(Loan loan) {
        repository.add(loan);
        return loan;
    }
    
    @Override
    public List<Loan> findAll() {
        return repository;
    }
    
    @Override
    public Optional<Loan> findById(String id) {
        for (Loan loan : repository)
            if (loan.getCustomerId().equals(id)) return Optional.of(loan);
        return Optional.empty();
    }
    
    @Override
    public void update(Loan loan) {
        Loan loanToUpdate = repository.stream()
                                      .filter(l -> l.getCustomerId().equals(loan.getCustomerId()))
                                      .collect(Collectors.toList())
                                      .get(0);
        loanToUpdate.setLoanAmount(loan.getLoanAmount())
                    .setManagers(loan.getManagers())
                    .setStatus(loan.getStatus())
                    .setTimeContractSent(loan.getTimeContractSent());
    }
}
