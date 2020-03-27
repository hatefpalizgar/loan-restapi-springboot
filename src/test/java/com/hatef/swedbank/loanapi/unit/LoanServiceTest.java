package com.hatef.swedbank.loanapi.unit;

import com.hatef.swedbank.loanapi.exception.ResourceNotFoundException;
import com.hatef.swedbank.loanapi.model.Decision;
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

import static com.hatef.swedbank.loanapi.model.LoanStatus.APPROVED;
import static com.hatef.swedbank.loanapi.model.LoanStatus.PENDING;
import static java.time.LocalDateTime.now;
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
    
    @MockBean
    Loan loan;
    
    @Test
    public void givenLoanService_ShouldReturnLoanSaved() {
        Loan loan = new Loan().setCustomerId("XX-XXXX-XXX").setLoanAmount(123.0);
        when(repository.save(loan)).thenReturn(loan);
        assertEquals(repository.save(loan), loanService.save(loan));
    }
    
    @Test
    public void findByInvalidCustomerId_shouldThrowException() {
        when(repository.findById("invalidID")).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> loanService.findByCustomerId("invalidID"));
    }
    
    @Test
    public void findByExistingCustomerId_shouldReturnLoan() {
        when(repository.findById("validId")).thenReturn(Optional.ofNullable(loan));
        assertEquals(loan, loanService.findByCustomerId("validId"));
    }
    
    @Test
    public void givenLoanToUpdate_shouldExistInLoans() {
        Loan updatedLoan = new Loan().setCustomerId("AA-BBBB-CCC").setLoanAmount(2000D);
        when(repository.findById("AA-BBBB-CCC")).thenReturn(Optional.of(updatedLoan));
        assertEquals(2000D, loanService.update(updatedLoan).getLoanAmount());
    }
    
    @Test
    public void givenManagerApproved_loanStatusShouldUpdate() {
        Decision decision = new Decision().setStatus(PENDING).setTimeStamp(now());
        loanService.approve("ALEX", decision);
        assertEquals(APPROVED, decision.getStatus());
    }
}