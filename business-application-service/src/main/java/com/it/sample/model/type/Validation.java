package com.it.sample.model.type;

import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@XmlRootElement
public class Validation {

    @XmlElement
    @JsonProperty
    private String object;

    @XmlElement
    @JsonProperty
    private String field;

    @XmlElement
    @JsonProperty
    private String value;

    @NotEmpty(message = "message cannot be null or empty")
    @XmlElement
    @JsonProperty
    private String message;
}