package com.github.arthurcech.eventsmanagement.controller.exception;

public class FieldMessage {

    private final String field;
    private final String message;

    public FieldMessage(
            String field,
            String message
    ) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
