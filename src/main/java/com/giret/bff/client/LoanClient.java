package com.giret.bff.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "loanClient", url = "${api.giret.loan.base.url}")
public interface LoanClient {



}
