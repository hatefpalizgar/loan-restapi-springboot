package com.hatef.swedbank.loanapi.unit;

import com.hatef.swedbank.loanapi.exception.ResourceNotFoundException;
import com.hatef.swedbank.loanapi.model.Loan;
import com.hatef.swedbank.loanapi.repository.LoanRepository;
import com.hatef.swedbank.loanapi.service.LoanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanServiceTest {
    
    @Autowired
    LoanService loanService;
    
    @MockBean
    LoanRepository repository;
    
    @Test
    public void givenLoanService_ShouldReturnLoanSaved() {
        Loan loan = new Loan().setCustomerId("XX-XXXX-XXX").setLoanAmount(123.0);
        when(repository.save(loan)).thenReturn(loan);
        assertEquals(repository.save(loan), loanService.save(loan));
    }
    
    @Test
    public void invalidCustomerId_shouldThrowException() {
        when(repository.findById("invalidID")).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> loanService.findByCustomerId("invalidID"));
    }
    
    @Test
    public void givenLoanToUpdate_shouldExistInLoans() {
        Loan updatedLoan = new Loan().setCustomerId("AA-BBBB-CCC").setLoanAmount(2000D);
        when(repository.findById("AA-BBBB-CCC")).thenReturn(Optional.of(updatedLoan));
        assertEquals(2000D, loanService.update(updatedLoan).getLoanAmount());
    }
}