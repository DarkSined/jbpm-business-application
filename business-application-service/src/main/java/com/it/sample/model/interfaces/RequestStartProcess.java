package com.it.sample.model.interfaces;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.it.sample.model.type.ProcessName;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestStartProcess {

    @Valid
    @JsonProperty
    private ProcessName processName;

    @NotEmpty(message = "correlationKey cannot be null or empty")
    @JsonProperty
    private String correlationKey;

    @JsonProperty
    private Object params;
}
