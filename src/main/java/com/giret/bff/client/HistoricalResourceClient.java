package com.giret.bff.client;

import com.giret.bff.model.HistoricalResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "historicalResourceClient", url = "${api.giret.resource.historical.base.url}")
public interface HistoricalResourceClient {

    @GetMapping(value = "/api/findAllHistoricalResource",produces = "application/json")
    List<HistoricalResource> findAllHistoricalResource();

    @GetMapping(value = "/api/findHistoricalResource/{id}",produces = "application/json")
    List<HistoricalResource> findHistoricalResource(@PathVariable Long id);

    @PostMapping(value = "/api/saveHistoricalResource",produces = "application/json")
    HistoricalResource saveHistoricalResource(@RequestBody HistoricalResource historicalResource);

}
