package com.reedhousesystems.services.core.profile.exception;


/**
 * User: porter
 * Date: 13/03/2012
 * Time: 08:58
 */
public class AuthenticationException extends BaseWebApplicationException {

    public AuthenticationException() {
        super(401, "40102", "Authentication Error", "Authentication Error. Either the username or password was incorrect");
    }


}
