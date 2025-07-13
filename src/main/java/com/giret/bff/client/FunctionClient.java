package com.giret.bff.client;

import com.giret.bff.model.UpdateLoan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.giret.bff.model.Resource;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "functionClient", url = "${azure.function.base.url}")
public interface FunctionClient {


    @PostMapping(value = "/api/updateResource", produces = "application/json")
    String updateResource(
            @RequestParam("code") String functionKey,
            @RequestBody Resource body
    );

    @PostMapping(value = "/api/updateLoan", produces = "application/json")
    String updateLoan(
            @RequestParam("code") String functionKey,
            @RequestBody UpdateLoan body
    );




}
