package com.it.sample.controllers;

import javax.validation.Valid;

import com.it.sample.model.interfaces.IntermediateSignal;
import com.it.sample.model.interfaces.RequestStartProcess;
import com.it.sample.model.interfaces.ResponseStartProcess;
import com.it.sample.services.ServiceHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("app/bpm/process")
@RestController
@Validated
public class ProcessHandler {

    @Autowired
    private ServiceHandler serviceHandler;

    @PostMapping("/start")
    public ResponseEntity<ResponseStartProcess> startProcess(
            @Valid @RequestBody RequestStartProcess requestStartProcess) {
        return ResponseEntity.ok().body(serviceHandler.startProcess(requestStartProcess));
    }

    @PostMapping("/wake-up-signal")
    public ResponseEntity<Void> wakeUpSignal(@Valid @RequestBody IntermediateSignal intermediateSignal) {
        serviceHandler.wakeUpSignal(intermediateSignal);
        return ResponseEntity.noContent().build();
    }
}
