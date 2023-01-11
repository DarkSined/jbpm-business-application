package com.it.sample.model.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.it.sample.model.type.Validation;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@XmlRootElement
public class ErrorResponse {

	@XmlElement
	@JsonProperty
	private String message;

	@JsonProperty
	private int code;

	@XmlElement
	@JsonProperty
	private LocalDateTime timestamp;

	@Valid
	@XmlElement
	@JsonProperty
	private List<Validation> validations;
}