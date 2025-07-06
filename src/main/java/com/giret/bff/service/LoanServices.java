package com.giret.bff.service;

import com.giret.bff.model.Loan;
import com.giret.bff.model.UpdateLoan;

import java.util.List;

public interface LoanServices {


    List<Loan> getAllLoans();
    List<Loan> getLoansByResourceId(Long resourceId);
    Loan saveLoan(Loan loan);
    Loan updateLoanByState(UpdateLoan body);

}
