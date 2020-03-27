package com.hatef.swedbank.loanapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();
}

