package com.hatef.swedbank.loanapi.controller.v1.api;

import com.hatef.swedbank.loanapi.exception.ResourceNotFoundException;
import com.hatef.swedbank.loanapi.model.Decision;
import com.hatef.swedbank.loanapi.model.Loan;
import com.hatef.swedbank.loanapi.model.LoanStatToJsonMapper;
import com.hatef.swedbank.loanapi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.hatef.swedbank.loanapi.model.LoanStatus.*;
import static java.time.LocalDateTime.now;


@Validated
@RestController
@RequestMapping("/api/v1/loan/")
public class LoanController {
    
    @Autowired
    LoanService loanService;
    
    @PostMapping("/request")
    public ResponseEntity<Loan> createRequest(@Valid @RequestBody Loan loan) {
        initializeLoan(loan);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }
    
    @PostMapping("/{manager}/approve")
    public ResponseEntity<Loan> makeDecision(@PathVariable String manager,
                                             @Valid @RequestBody Loan loan) throws ResourceNotFoundException {
        loanValidateAndApprove(manager, loan);
        sendLoanToCustomer(loan);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }
    
    @GetMapping("/stats")
    public LoanStatToJsonMapper showStatistics(@Value(value = "${app.loan.stat.timeout:60}") long timeout) {
        List<Loan> loans = findValidSentContracts(timeout);
        return new LoanStatToJsonMapper()
                .setCount(loans.size())
                .setSum(getDoubleStreamFrom(loans).sum())
                .setAvg(getDoubleStreamFrom(loans).average().orElse(0))
                .setMax(getDoubleStreamFrom(loans).max().orElse(0))
                .setMin(getDoubleStreamFrom(loans).min().orElse(0));
    }
    
    private void initializeLoan(@RequestBody @Valid Loan loan) {
        loan.setStatus(PENDING).getManagers().forEach(m -> {
            loan.getDecisionMap().put(m, new Decision(PENDING, now()));
        });
        loanService.save(loan);
    }
    
    private void loanValidateAndApprove(@PathVariable String manager, @RequestBody @Valid Loan loan) {
        String customerId = loan.getCustomerId();
        Optional<Loan> loanToDecide = Optional.ofNullable(loanService.findByCustomerId(customerId));
        if(loanToDecide.isPresent() && loan.getManagers().contains(manager)){
            loan.getDecisionMap().computeIfPresent(manager, loanService::approve);
        }else if(! loan.getManagers().contains(manager)){
            throw new ResourceNotFoundException(String.format("Manager with name: '%s' not found", manager));
        }else{
            throw new ResourceNotFoundException(String.format("Loan for customer id: '%s' not found", customerId));
        }
    }
    
    private void sendLoanToCustomer(@RequestBody @Valid Loan loan) {
        if(loan.getDecisionMap().values().stream().allMatch(v -> v.getStatus() == APPROVED)){
            loan.setStatus(SENT).setTimeContractSent(now());
        }
        loanService.update(loan);
    }
    
    private List<Loan> findValidSentContracts(@Value("${app.loan.stat.timeout}") long timeout) {
        return loanService
                .findAll()
                .stream()
                .filter(l -> l.getStatus() == SENT)
                .filter(l -> {
                    assert l.getTimeContractSent() != null;
                    return Math.abs(ChronoUnit.SECONDS.between(l.getTimeContractSent(), now())) <= timeout;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    private DoubleStream getDoubleStreamFrom(List<Loan> loans) {
        return loans.stream().mapToDouble(Loan::getLoanAmount);
    }
}
