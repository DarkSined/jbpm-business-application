package com.it.sample.components.signals;

import com.it.sample.components.BaseComponent;
import com.it.sample.components.annotations.LogExecutionTime;
import com.it.sample.model.interfaces.IntermediateSignal;
import com.it.sample.model.interfaces.RequestStartProcess;
import com.it.sample.model.interfaces.ResponseStartProcess;

import org.jbpm.services.api.ProcessService;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.process.CorrelationKey;
import org.springframework.stereotype.Component;

@Component("WaitEvent")
public class WaitEvent extends BaseComponent {

    @Override
    public ResponseStartProcess startProcess(ProcessService processService, CorrelationKey correlationKey, RequestStartProcess requestStartProcess) {
        // Not Implemented
        return null;
    }

    @LogExecutionTime
    @Override
    public void wakeUpSignal(ProcessService processService, IntermediateSignal intermediateSignal) {
        ProcessInstance processInstance = processService.getProcessInstance(intermediateSignal.getProcessInstanceId());
        processService.signalProcessInstance(processInstance.getId(), intermediateSignal.getSignalName().getValue(),
                intermediateSignal.getBody());
    }
}
