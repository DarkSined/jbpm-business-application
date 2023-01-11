package com.it.sample.model.interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.it.sample.model.type.ProcessName;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseStartProcess {
    
    @JsonProperty
    private Long processInstanceId;

    @JsonProperty
    private String correlationKey;

    @JsonProperty
    private ProcessName processName;

    @JsonProperty
    private Object requestParameters;
}
