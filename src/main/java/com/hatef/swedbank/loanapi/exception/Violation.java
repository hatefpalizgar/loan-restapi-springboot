package com.hatef.swedbank.loanapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Violation {
    private String fieldName;
    private String message;
}
