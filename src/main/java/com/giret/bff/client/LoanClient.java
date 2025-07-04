package com.giret.bff.client;


import com.giret.bff.model.Loan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "loanClient", url = "${api.giret.loan.base.url}")
public interface LoanClient {

    @GetMapping(value = "/api/findAllLoan",produces = "application/json")
    List<Loan> findAllLoan();

    @GetMapping(value = "/api/findLoandByResource/{id}",produces = "application/json")
    List<Loan> findLoandByResource(@PathVariable("id")Long id);

    @PostMapping(value = "/api/saveLoan",produces = "application/json")
    Loan saveLoan(@RequestBody Loan loan);

    @PutMapping(value = "/updateLoanByState/{state}/{id}",produces = "application/json")
    Loan updateLoanByState(@PathVariable ("state")String state,@PathVariable ("id")Long id);


}
