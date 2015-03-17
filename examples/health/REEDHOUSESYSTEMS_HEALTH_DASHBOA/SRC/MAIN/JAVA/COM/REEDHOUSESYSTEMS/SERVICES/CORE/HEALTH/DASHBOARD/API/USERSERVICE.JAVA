package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.AuthenticatedUserToken;
import com.reedhousesystems.services.core.health.dashboard.model.Contact;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.model.Role;
import com.reedhousesystems.services.core.health.dashboard.requests.CreateUserRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.LoginRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.PasswordRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateContactsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateStatusRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateUserRequest;

public interface UserService {

    /**
     * Create a new User with the given role
     *
     * @param request
     * @param role
     */
    public void createUser(CreateUserRequest request);


    /**
     * Create a Default User with a given role
     *
     * @param role
     * @return AuthenticatedUserToken
     */
    public AuthenticatedUserToken createUser(Role role);

    /**
     * Update User
     *
     * @param userId
     * @param request
     */
    public ExternalUser updateUser(String userId, UpdateUserRequest request);
    
    /**
     * Update user
     * 
     * @param userId
     * @param request
     */
    public void updateUser(String userId, UpdateContactsRequest request);
    
    /**
     * 
     * @param userId
     * @param request
     */
    public void updateUser(String userId, UpdateStatusRequest request);

    /**
     * Delete user, only authenticated user accounts can be deleted
     *
     * @param userMakingRequest the user authorized to delete the user
     * @param userId the id of the user to delete
     */
    public void deleteUser(ExternalUser userMakingRequest, String userId);

    /**
     * Get a User based on a unique identifier
     *
     * Identifiers supported are uuid
     *
     * @param userIdentifier
     * @return  User
     */
    public ExternalUser getUser(ExternalUser requestingUser, String userIdentifier);
//    public ExternalUser getUser(String userIdentifier);
    
    /**
     * 
     * @param requestingUser
     * @param userIdentifier
     * @return
     */
    public Contact getContacts(ExternalUser requestingUser, String userIdentifier);
    
    /**
     * Get all users
     * 
     * @return List of users
     */
    public List<ExternalUser> getAllUsers(ExternalUser requestingUser);

    /**
     * Login a User
     *
     * @param request
     * @return AuthenticatedUserToken
     */
    public AuthenticatedUserToken login(LoginRequest request);
    
    /**
     * Reset user password
     * 
     * @param userId
     * @param passwordRequest
     */
    public void resetPassword(ExternalUser userMakingRequest, String userId, PasswordRequest passwordRequest);

    /**
     * Delete all SessionToken objects that have not been accessed within the duration specified by the argument timeSinceLastUpdatedInMinutes
     *
     * @param timeSinceLastUpdatedInMinutes
     * @return the number of sessions removed
     */
    public Integer deleteExpiredSessions(int timeSinceLastUpdatedInMinutes);

}
