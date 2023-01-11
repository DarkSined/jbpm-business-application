package com.it.sample.services;

import com.it.sample.components.BaseComponent;
import com.it.sample.model.interfaces.IntermediateSignal;
import com.it.sample.model.interfaces.RequestStartProcess;
import com.it.sample.model.interfaces.ResponseStartProcess;

import org.jbpm.services.api.ProcessService;
import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationKey;
import org.kie.internal.process.CorrelationKeyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceHandler {

    @Autowired
    private ProcessService processService;

    @Autowired
    private ApplicationContext applicationContext;

    private CorrelationKeyFactory correlationKeyFactory = KieInternalServices.Factory.get()
            .newCorrelationKeyFactory();

    public ResponseStartProcess startProcess(RequestStartProcess requestStartProcess) {
        ResponseStartProcess responseStartProcess = null;
        CorrelationKey correlationKey = correlationKeyFactory
                .newCorrelationKey(requestStartProcess.getCorrelationKey());
        BaseComponent baseComponent = (BaseComponent) applicationContext.getBean(requestStartProcess.getProcessName().getValue());
        responseStartProcess = baseComponent.startProcess(processService, correlationKey, requestStartProcess);
        log.info("Created correlationKey: {}", correlationKey.getName());
        return responseStartProcess;
    }

    @Async
    public void wakeUpSignal(IntermediateSignal intermediateSignal) {
        BaseComponent baseComponent = (BaseComponent) applicationContext.getBean(intermediateSignal.getSignalName().getValue());
        baseComponent.wakeUpSignal(processService, intermediateSignal);
        log.info("Intermediate signal waked up for correlationKey: {}", intermediateSignal.getCorrelationKey());
    }
}
