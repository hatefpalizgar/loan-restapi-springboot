package com.hatef.swedbank.loanapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LoanStatToJsonMapper {
    int count;
    double sum;
    double avg;
    double max;
    double min;
}
