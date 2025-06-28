package com.giret.bff.service.impl;

import com.giret.bff.client.HistoricalResourceClient;
import com.giret.bff.model.HistoricalResource;
import com.giret.bff.service.HistoricalResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoricalResourceServiceImpl implements HistoricalResourceService {

    @Autowired
    HistoricalResourceClient historicalResourceClient;


    @Override
    public List<HistoricalResource> findAllHistoricalResource() {
        return historicalResourceClient.findAllHistoricalResource();
    }

    @Override
    public List<HistoricalResource> findHistoricalByResourceId(Long recursoId) {
        return historicalResourceClient.findHistoricalResource(recursoId);
    }

    @Override
    public HistoricalResource saveHistoricalResource(HistoricalResource historicalResource) {
        return historicalResourceClient.saveHistoricalResource(historicalResource);
    }
}
