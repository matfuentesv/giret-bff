package com.giret.bff.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import com.giret.bff.model.Loan;
import com.giret.bff.model.Resource;

@FeignClient(name = "functionClient", url = "${azure.function.base-url}")
public interface FunctionClient {

  
    String updateResource(@RequestBody Resource body);

  
    String createLoan(@RequestBody Loan body);


}
