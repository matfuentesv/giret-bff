package com.giret.bff.service;

import com.giret.bff.model.HistoricalResource;
import java.util.List;

public interface HistoricalResourceService {

    List<HistoricalResource> findAllHistoricalResource();
    List<HistoricalResource> findHistoricalByResourceId(Long recursoId);
    HistoricalResource saveHistoricalResource(HistoricalResource historicalResource);

}
