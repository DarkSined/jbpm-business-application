package com.it.sample.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SignalName {
    UNKNOWN("Unknown"), 
    WAIT_EVENT("WaitEvent");

    private String value;

    SignalName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SignalName getValue(String value) {
        for (SignalName e : SignalName.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
