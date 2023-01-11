package com.it.sample.model.interfaces;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.it.sample.model.type.ProcessName;
import com.it.sample.model.type.SignalName;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class IntermediateSignal {

    @NotEmpty(message = "correlationKey cannot be null or empty")
    @JsonProperty
    private String correlationKey;

    @Positive(message = "processInstanceId cannot be zero or negative")
    @JsonProperty
    private Long processInstanceId;

    @Valid
    @JsonProperty
    private SignalName signalName;

    @Valid
    @JsonProperty
    private ProcessName processName;

    @JsonProperty
    private Object body;
}
