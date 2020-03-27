package com.hatef.swedbank.loanapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Decision {
    private LoanStatus status;
    private LocalDateTime timeStamp;
}
