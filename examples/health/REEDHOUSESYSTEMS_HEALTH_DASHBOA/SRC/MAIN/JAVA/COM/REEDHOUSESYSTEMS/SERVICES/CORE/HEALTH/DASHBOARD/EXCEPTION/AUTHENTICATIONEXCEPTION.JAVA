package com.reedhousesystems.services.core.health.dashboard.exception;


/**
 * User: C. Gremu
 */
public class AuthenticationException extends BaseWebApplicationException {

    public AuthenticationException() {
        super(401, "40102", "Authentication Error", "Authentication Error. The username or password were incorrect");
    }


}
