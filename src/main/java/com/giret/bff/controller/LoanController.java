package com.giret.bff.controller;

import com.giret.bff.model.Loan;
import com.giret.bff.model.UpdateLoan;
import com.giret.bff.service.LoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bff/loan")
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanServices loanServices;


    @GetMapping("/findAll")
    public ResponseEntity<List<Loan>> findAllLoan() {
        return ResponseEntity.ok(loanServices.getAllLoans());
    }

    @GetMapping("/findLoandByResource/{id}")
    public ResponseEntity<List<Loan>> findLoandByResource(@PathVariable("id")Long id) {
        return ResponseEntity.ok(loanServices.getLoansByResourceId(id));
    }

    @PostMapping("/saveLoan")
    public ResponseEntity<Loan> saveLoan(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanServices.saveLoan(loan));
    }

    @PutMapping("/updateLoanByState")
    public ResponseEntity<Loan> updateLoanByState(@RequestBody UpdateLoan body) {
        return ResponseEntity.ok(loanServices.updateLoanByState(body));
    }


}
