package com.hatef.swedbank.loanapi.model;

import lombok.*;
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
