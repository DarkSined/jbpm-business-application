package com.it.sample.components.workitems;

import java.util.HashMap;
import java.util.Map;

import com.it.sample.components.annotations.LogExecutionTime;

import org.jbpm.process.workitem.core.AbstractLogOrThrowWorkItemHandler;
import org.jbpm.process.workitem.core.util.RequiredParameterValidator;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("RandomNumberWorkItem")
public class RandomNumberWorkItem extends AbstractLogOrThrowWorkItemHandler {
    
    @LogExecutionTime
    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
        try {
            RequiredParameterValidator.validate(this.getClass(), workItem);
            Integer min = Integer.valueOf(String.valueOf(workItem.getParameter("min")));
            Integer max = Integer.valueOf(String.valueOf(workItem.getParameter("max")));
            log.info("Starting calculating randomNumber with range min: {} - max: {}", min, max);
            double randomNumber = Math.random() * (max - min);
            Map<String, Object> results = new HashMap<>();
            results.put("randomNumber", randomNumber);
            workItemManager.completeWorkItem(workItem.getId(), results);
            log.info("Calculated randomNumber: {}", randomNumber);
        } catch (Exception e) {
            handleException(e.getCause());
        }
    }

    @LogExecutionTime
    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
        workItemManager.abortWorkItem(workItem.getId());
        log.warn("Work Item Aborted !");
    }
}
