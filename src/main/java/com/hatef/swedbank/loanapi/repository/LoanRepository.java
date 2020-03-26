package com.hatef.swedbank.loanapi.repository;

import com.hatef.swedbank.loanapi.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
}
