package com.it.sample.exceptions;

import com.it.sample.model.type.ProcessName;
import com.it.sample.model.type.SignalName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SystemApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final SignalName signalName;
	private final ProcessName processName;

	public SystemApplicationException(Throwable th) {
		super(th);
		this.signalName = SignalName.UNKNOWN;
		this.processName = ProcessName.UNKNOWN;
	}

	public SystemApplicationException(String message) {
		super(message);
		this.signalName = SignalName.UNKNOWN;
		this.processName = ProcessName.UNKNOWN;
	}

	public SystemApplicationException(String message, Throwable th) {
		super(message, th);
		this.signalName = SignalName.UNKNOWN;
		this.processName = ProcessName.UNKNOWN;
	}

	public SystemApplicationException(String message, Throwable th, ProcessName processName) {
		super(String.format("ProcessName: %s - Message: %s", processName.getValue(), message), th);
		this.processName = processName;
		this.signalName = SignalName.UNKNOWN;
	}

	public SystemApplicationException(String message, Throwable th, ProcessName processName, SignalName signalName) {
		super(String.format("ProcessName: %s - SignalName: %s - Message: %s", processName.getValue(),
				signalName.getValue(), message), th);
		this.processName = processName;
		this.signalName = signalName;
	}
}