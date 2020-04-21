package com.hatef.swedbank.loanapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatef.swedbank.loanapi.controller.v1.api.LoanController;
import com.hatef.swedbank.loanapi.model.Decision;
import com.hatef.swedbank.loanapi.model.Loan;
import com.hatef.swedbank.loanapi.model.LoanStatToJsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.hatef.swedbank.loanapi.model.LoanStatus.PENDING;
import static java.time.LocalDateTime.now;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class LoanControllerTest {
    
    @Autowired
    MockMvc mvc;
    
    @MockBean
    LoanController loanController;
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public Loan setup() {
        Loan testLoan;
        Map<String, Decision> decisionMap = new HashMap<>();
        Decision pending = new Decision(PENDING, now());
        decisionMap.put("ALEX", pending);
        testLoan = new Loan()
                .setCustomerId("XX-XXXX-XXX")
                .setLoanAmount(12345D)
                .setManagers(Arrays.asList("ALEX"));
        return testLoan;
    }
    
    @Test
    public void givenLoanDetails_whenSendRequest_thenReturnLoanRequest() throws Exception {
        Loan loan = setup();
        given(loanController.createRequest(loan)).willReturn(new ResponseEntity<>(loan, HttpStatus.OK));
        MvcResult mvcResult = mvc.perform(post("/api/v1/loan/request")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .characterEncoding("utf-8")
                                                  .content(asJsonString(loan)))
                                 .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(asJsonString(loan), mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void givenLoanRequest_whenManagerApproves_thenChangeLoanStatusToApproved() throws Exception {
        Loan loan = setup();
        given(loanController.makeDecision("ALEX", loan)).willReturn(new ResponseEntity<>(loan, HttpStatus.OK));
        MvcResult mvcResult = mvc.perform(post("/api/v1/loan/ALEX/approve")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .characterEncoding("utf-8")
                                                  .content(asJsonString(loan))).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
    
    @Test
    public void givenSomeLoanIsSentRecently_shouldReturnStatistics() throws Exception {
        LoanStatToJsonMapper stats = new LoanStatToJsonMapper()
                .setCount(1)
                .setSum(12345.0)
                .setAvg(12345.0)
                .setMax(12345.0)
                .setMin(12345.0);
        given(loanController.showStatistics(60)).willReturn(stats);
        MvcResult mvcResult = mvc.perform(get("/api/v1/loan/stats")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .characterEncoding("utf-8")).andReturn();
        assertEquals(asJsonString(stats), mvcResult.getResponse().getContentAsString());
    }
}
