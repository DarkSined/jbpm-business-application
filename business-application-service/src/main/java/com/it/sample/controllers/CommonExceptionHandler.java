package com.it.sample.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.it.sample.exceptions.SystemApplicationException;
import com.it.sample.model.interfaces.ErrorResponse;
import com.it.sample.model.type.Validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import io.undertow.server.RequestTooBigException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	@Value("${spring.servlet.multipart.max-request-size}")
	private String maxRequestSize;

	@Value("${spring.servlet.multipart.max-file-size}")
	private String maxFileSize;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { BindException.class, MethodArgumentNotValidException.class })
	public ErrorResponse handleBindException(BindException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		List<Validation> validations = new ArrayList<>();
		ErrorResponse response = new ErrorResponse();

		ex.getFieldErrors().forEach(err -> {
			Validation val = new Validation();
			val.setObject(err.getObjectName());
			val.setField(err.getField());
			val.setValue((String.valueOf(err.getRejectedValue())));
			val.setMessage(err.getDefaultMessage());
			validations.add(val);
		});

		ex.getGlobalErrors().forEach(err -> {
			Validation val = new Validation();
			val.setObject("root");
			val.setMessage(err.getDefaultMessage());
			validations.add(val);
		});

		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Invalid request");
		response.setTimestamp(LocalDateTime.now());
		response.setValidations(validations);
		return response;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MissingServletRequestParameterException.class })
	public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
			WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		List<Validation> validations = new ArrayList<>();
		Validation val = new Validation();

		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Missing required parameter");
		response.setTimestamp(LocalDateTime.now());
		val.setField(ex.getParameterName());
		val.setMessage("Missing parameter " + ex.getParameterName());
		validations.add(val);
		response.setValidations(validations);
		return response;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		List<Validation> validations = new ArrayList<>();
		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Request not parsable");

		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException ifx = (InvalidFormatException) ex.getCause();
			for (Reference e : ifx.getPath()) {
				Validation val = new Validation();
				val.setField(e.getFieldName());
				val.setValue(String.valueOf(ifx.getValue()));
				if (ifx.getTargetType() != null && ifx.getTargetType().isEnum()) {
					val.setMessage(
							String.format("Invalid value, must be one of: %s.",
									Arrays.toString(ifx.getTargetType().getEnumConstants())));
				}
			}
		}

		if (ex.getCause() instanceof ValueInstantiationException) {
			ValueInstantiationException vie = (ValueInstantiationException) ex.getCause();
			for (Reference e : vie.getPath()) {
				Validation val = new Validation();
				val.setField(e.getFieldName());
				val.setMessage("Invalid value");
				validations.add(val);
			}
		}
		response.setValidations(validations);
		return response;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		List<Validation> validations = new ArrayList<>();

		ex.getConstraintViolations().forEach(err -> {
			Validation val = new Validation();
			val.setMessage(err.getMessage());
			val.setValue(String.valueOf(err.getInvalidValue()));
			for (Node node : err.getPropertyPath()) {
				val.setField(node.getName());
			}
			validations.add(val);
		});

		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Violation of constraints");
		response.setTimestamp(LocalDateTime.now());
		response.setValidations(validations);
		return response;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MissingRequestHeaderException.class })
	public ErrorResponse handleMissingRequestHeaderException(MissingRequestHeaderException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		List<Validation> validations = new ArrayList<>();
		Validation val = new Validation();

		val.setField(ex.getHeaderName());
		val.setMessage("Missing valorization of a required header " + ex.getHeaderName());
		validations.add(val);

		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Missing HTTP header");
		response.setTimestamp(LocalDateTime.now());
		response.setValidations(validations);
		return response;
	}

	@ExceptionHandler(value = { ConversionFailedException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleConversionFailedException(ConversionFailedException ex, WebRequest request)
			throws ClassNotFoundException {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		List<Validation> validations = new ArrayList<>();
		Validation val = new Validation();

		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Invalid request");
		response.setTimestamp(LocalDateTime.now());
		if (!ObjectUtils.isEmpty(ex.getValue()) && ex.getTargetType().getType().isEnum()) {
			Class<?> c = Class.forName(ex.getTargetType().getName());
			Object value = ex.getValue();
			if (value != null) {
				val.setValue(value.toString());
			}
			val.setMessage(String.format("Invalid value, must be one of: %s.", Arrays.asList(c.getEnumConstants())));
			validations.add(val);
		}
		response.setValidations(validations);
		return response;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { SystemApplicationException.class })
	public ErrorResponse handleSystemApplicationException(SystemApplicationException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		return response;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MissingServletRequestPartException.class })
	public ErrorResponse handleMissingServletRequestPartException(MissingServletRequestPartException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		List<Validation> validations = new ArrayList<>();
		Validation val = new Validation();
		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage("Invalid multipart request");
		response.setTimestamp(LocalDateTime.now());
		val.setField(ex.getRequestPartName());
		val.setMessage(ex.getMessage());
		validations.add(val);
		response.setValidations(validations);
		return response;
	}

	@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
	@ExceptionHandler(value = { MaxUploadSizeExceededException.class })
	public ErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		response.setCode(HttpStatus.PAYLOAD_TOO_LARGE.value());
		response.setMessage("File exceed limit upload, max limit permitted: " + maxFileSize);
		response.setTimestamp(LocalDateTime.now());
		return response;
	}

	@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
	@ExceptionHandler(value = { RequestTooBigException.class })
	public ErrorResponse handleRequestTooBigException(RequestTooBigException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		response.setCode(HttpStatus.PAYLOAD_TOO_LARGE.value());
		response.setMessage("Request exceed limit, max limit permitted: " + maxRequestSize);
		response.setTimestamp(LocalDateTime.now());
		return response;
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		ErrorResponse response = new ErrorResponse();
		response.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
		response.setMessage("HTTP Method not allowed for this API");
		response.setTimestamp(LocalDateTime.now());
		return response;
	}
}