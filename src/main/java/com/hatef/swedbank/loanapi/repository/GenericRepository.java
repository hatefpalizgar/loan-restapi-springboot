package com.hatef.swedbank.loanapi.repository;

import com.hatef.swedbank.loanapi.model.Loan;

import java.util.List;
import java.util.Optional;


public interface GenericRepository<T> {
    T save(T t);
    List<T> findAll();
    Optional<T> findById(String id);
    
    void update(Loan loanToUpdate);
}
