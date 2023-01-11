package com.it.sample.components;

import java.util.LinkedHashMap;

import com.it.sample.model.interfaces.IntermediateSignal;
import com.it.sample.model.interfaces.RequestStartProcess;
import com.it.sample.model.interfaces.ResponseStartProcess;

import org.jbpm.services.api.ProcessService;
import org.kie.internal.process.CorrelationKey;
import org.springframework.stereotype.Component;

@Component("BaseComponent")
public abstract class BaseComponent {

    public abstract ResponseStartProcess startProcess(ProcessService processService, CorrelationKey correlationKey,
            RequestStartProcess requestStartProcess);

    public abstract void wakeUpSignal(ProcessService processService, IntermediateSignal intermediateSignal);

    @SuppressWarnings("unchecked")
    protected final LinkedHashMap<String, Object> retrieveGenericParams(RequestStartProcess requestStartProcess) {
        return (LinkedHashMap<String, Object>) requestStartProcess.getParams();
    }
}
