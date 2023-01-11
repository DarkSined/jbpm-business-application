package com.it.sample.components.processes;

import java.util.LinkedHashMap;

import com.it.sample.components.BaseComponent;
import com.it.sample.components.annotations.LogExecutionTime;
import com.it.sample.configurations.AppPropertiesConfig;
import com.it.sample.model.interfaces.IntermediateSignal;
import com.it.sample.model.interfaces.RequestStartProcess;
import com.it.sample.model.interfaces.ResponseStartProcess;

import org.jbpm.services.api.ProcessService;
import org.kie.internal.process.CorrelationKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("SampleProcess")
public class SampleProcess extends BaseComponent {

    @Autowired
    AppPropertiesConfig appPropertiesConfig;

    @LogExecutionTime
    @Override
    public ResponseStartProcess startProcess(ProcessService processService, CorrelationKey correlationKey,
            RequestStartProcess requestStartProcess) {
        LinkedHashMap<String, Object> request = retrieveGenericParams(requestStartProcess);
        Long processInstanceId = processService.startProcess(appPropertiesConfig.getDeploymendId(),
                requestStartProcess.getProcessName().getValue(),
                correlationKey, request);
        return ResponseStartProcess.builder().correlationKey(correlationKey.getName()).requestParameters(request).processName(requestStartProcess.getProcessName())
                .processInstanceId(processInstanceId).build();
    }

    @Override
    public void wakeUpSignal(ProcessService processService, IntermediateSignal intermediateSignal) {
        // Not Implemented
    }
}
