package com.it.sample.components.listeners;

import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BPMProcessEventListener implements ProcessEventListener {

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent processNodeLeftEvent) {
        String processName = processNodeLeftEvent.getProcessInstance().getProcessName();
        Long processId = processNodeLeftEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processNodeLeftEvent.getProcessInstance().getParentProcessInstanceId();
        String nodeName = processNodeLeftEvent.getNodeInstance().getNodeName();
        Long nodeId = processNodeLeftEvent.getNodeInstance().getNodeId();
        log.info(
                "Completed node instance processName: {}, processId: {}, parentProcessId: {}, nodeName: {}, nodeId: {}",
                processName, processId, parentProcessInstanceId, nodeName, nodeId);
    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {
        String processName = processNodeTriggeredEvent.getProcessInstance().getProcessName();
        Long processId = processNodeTriggeredEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processNodeTriggeredEvent.getProcessInstance().getParentProcessInstanceId();
        Long nodeId = processNodeTriggeredEvent.getNodeInstance().getNodeId();
        String nodeName = processNodeTriggeredEvent.getNodeInstance().getNodeName();
        log.debug("Triggered processName: {}, processId: {}, parentProcessId: {}, nodeName: {}, nodeId: {}",
                processName, processId, parentProcessInstanceId, nodeName, nodeId);

    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent processCompletedEvent) {
        String processName = processCompletedEvent.getProcessInstance().getProcessName();
        Long processId = processCompletedEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processCompletedEvent.getProcessInstance().getParentProcessInstanceId();
        log.info("Completed processName: {}, processId: {}, parentProcessId: {}", processName, processId,
                parentProcessInstanceId);
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent processStartedEvent) {
        String processName = processStartedEvent.getProcessInstance().getProcessName();
        Long processId = processStartedEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processStartedEvent.getProcessInstance().getParentProcessInstanceId();
        log.info("Started processName: {}, processId: {}, parentProcessId: {}", processName, processId,
                parentProcessInstanceId);
    }

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent processVariableChangedEvent) {
        Object newValue = processVariableChangedEvent.getNewValue();
        Object oldValue = processVariableChangedEvent.getOldValue();
        String variableId = processVariableChangedEvent.getVariableId();
        String variableInstanceId = processVariableChangedEvent.getVariableInstanceId();
        String processName = processVariableChangedEvent.getProcessInstance().getProcessName();
        Long processId = processVariableChangedEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processVariableChangedEvent.getProcessInstance().getParentProcessInstanceId();
        log.debug(
                "Changed variable value processName: {}, processId: {}, parentProcessId: {}, variableId: {}, variableInstanceId: {}, oldValue: {}, newValue: {}",
                processName, processId, parentProcessInstanceId, variableId, variableInstanceId, oldValue,
                newValue);
    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent processNodeLeftEvent) {
        String processName = processNodeLeftEvent.getProcessInstance().getProcessName();
        Long processId = processNodeLeftEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processNodeLeftEvent.getProcessInstance().getParentProcessInstanceId();
        String nodeName = processNodeLeftEvent.getNodeInstance().getNodeName();
        Long nodeId = processNodeLeftEvent.getNodeInstance().getNodeId();
        log.info(
                "Starting node instance processName: {}, processId: {}, parentProcessId: {}, nodeName: {}, nodeId: {}",
                processName, processId, parentProcessInstanceId, nodeName, nodeId);
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {
        String processName = processNodeTriggeredEvent.getProcessInstance().getProcessName();
        Long processId = processNodeTriggeredEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processNodeTriggeredEvent.getProcessInstance().getParentProcessInstanceId();
        Long nodeId = processNodeTriggeredEvent.getNodeInstance().getNodeId();
        String nodeName = processNodeTriggeredEvent.getNodeInstance().getNodeName();
        log.debug(
                "Triggering processName: {}, processId: {}, parentProcessId: {}, nodeName: {}, nodeId: {}",
                processName, processId, parentProcessInstanceId, nodeName, nodeId);
    }

    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent processCompletedEvent) {
        String processName = processCompletedEvent.getProcessInstance().getProcessName();
        Long processId = processCompletedEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processCompletedEvent.getProcessInstance().getParentProcessInstanceId();
        log.debug("Completing processName: {}, processId: {}, parentProcessId: {}", processName, processId,
                parentProcessInstanceId);
    }

    @Override
    public void beforeProcessStarted(ProcessStartedEvent processStartedEvent) {
        String processName = processStartedEvent.getProcessInstance().getProcessName();
        Long processId = processStartedEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processStartedEvent.getProcessInstance().getParentProcessInstanceId();
        log.info("Starting processName: {}, processId: {}, parentProcessId: {}", processName, processId,
                parentProcessInstanceId);
    }

    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent processVariableChangedEvent) {
        Object newValue = processVariableChangedEvent.getNewValue();
        Object oldValue = processVariableChangedEvent.getOldValue();
        String variableId = processVariableChangedEvent.getVariableId();
        String variableInstanceId = processVariableChangedEvent.getVariableInstanceId();
        String processName = processVariableChangedEvent.getProcessInstance().getProcessName();
        Long processId = processVariableChangedEvent.getProcessInstance().getId();
        Long parentProcessInstanceId = processVariableChangedEvent.getProcessInstance().getParentProcessInstanceId();
        log.debug(
                "Changing variable value processName: {}, processId: {}, parentProcessId: {}, variableId: {}, variableInstanceId: {}, oldValue: {}, newValue: {}",
                processName, processId, parentProcessInstanceId, variableId, variableInstanceId, oldValue,
                newValue);
    }
}
