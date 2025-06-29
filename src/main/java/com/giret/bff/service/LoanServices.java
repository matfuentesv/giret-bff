package com.giret.bff.service;

import com.giret.bff.model.Loan;

import java.util.List;

public interface LoanServices {

    Loan saveLoan(Loan loan);
    List<Loan> getAllLoans();
    List<Loan> getLoansByResourceId(Long resourceId);

}
