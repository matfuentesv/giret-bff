package com.giret.bff.service.impl;

import com.giret.bff.client.LoanClient;
import com.giret.bff.model.Loan;
import com.giret.bff.service.LoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServicesImpl implements LoanServices {

    @Autowired
    LoanClient loanClient;



    @Override
    public List<Loan> getAllLoans() {
        return loanClient.findAllLoan();
    }

    @Override
    public List<Loan> getLoansByResourceId(Long resourceId) {
        return loanClient.findLoandByResource(resourceId);
    }

    @Override
    public Loan saveLoan(Loan loan) {
        return loanClient.saveLoan(loan);
    }
}
