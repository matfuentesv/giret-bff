package com.giret.bff.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.giret.bff.model.Loan;
import com.giret.bff.model.Resource;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "functionClient", url = "${azure.function.base-url}")
public interface FunctionClient {


    @PostMapping(value = "/api/updateResource", produces = "application/json")
    String updateResource(
            @RequestParam("code") String functionKey,
            @RequestBody Resource body
    );

  
    String createLoan(@RequestBody Loan body);


}
