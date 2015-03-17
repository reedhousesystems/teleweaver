package com.reedhousesystems.services.core.health.dashboard.exception;


public class ApplicationRuntimeException extends BaseWebApplicationException {

    public ApplicationRuntimeException(String applicationMessage) {
        super(500, "50002", "Internal System error", applicationMessage);
    }
}
