package com.reedhousesystems.services.core.profile.api;

import java.util.List;

import com.reedhousesystems.services.core.profile.dto.DemographicDTO;
import com.reedhousesystems.services.core.profile.model.AuthenticatedUserToken;
import com.reedhousesystems.services.core.profile.model.Contact;
import com.reedhousesystems.services.core.profile.model.Demographic;
import com.reedhousesystems.services.core.profile.model.ExternalUser;
import com.reedhousesystems.services.core.profile.model.Role;
import com.reedhousesystems.services.core.profile.request.ContactRequest;
import com.reedhousesystems.services.core.profile.request.CreateUserRequest;
import com.reedhousesystems.services.core.profile.request.LoginRequest;
import com.reedhousesystems.services.core.profile.request.PasswordRequest;
import com.reedhousesystems.services.core.profile.request.StatusRequest;
import com.reedhousesystems.services.core.profile.request.UserRequest;

public interface UserService {


    /**
     * Create a new User with the given role
     *
     * @param request
     * @param role
     * @return AuthenticatedUserToken
     */
    public AuthenticatedUserToken createUser(CreateUserRequest request);


    /**
     * Create a Default User with a given role
     *
     * @param role
     * @return AuthenticatedUserToken
     */
    public AuthenticatedUserToken createUser(Role role);

    /**
     * Save User
     *
     * @param userId
     * @param request
     */
    public void updateUser(String userId, UserRequest request);
    
    /**
     * Save user
     * @param userId
     * @param request
     */
    public void updateUser(String userId, DemographicDTO demographicDTO);
    
    /**
     * Save user
     * 
     * @param userId
     * @param request
     */
    public void updateUser(String userId, ContactRequest request);
    
    /**
     * 
     * @param userId
     * @param request
     */
    public void updateUser(String userId, StatusRequest request);
    
    /**
     * 
     * @param userId
     * @param request
     */
    public void changePassword(String userId, PasswordRequest request);

    /**
     * Delete user, only authenticated user accounts can be deleted
     *
     * @param userMakingRequest the user authorized to delete the user
     * @param userId the id of the user to delete
     */
    public void deleteUser(String userId);

    /**
     * Get a User based on a unique identifier
     *
     * Identifiers supported are uuid, emailAddress
     *
     * @param userIdentifier
     * @return  User
     */    
    public ExternalUser getUser(ExternalUser requestingUser, String userIdentifier);
    
    /**
     * 
     * @param requestingUser
     * @param userIdentifier
     * @return
     */
    public Contact getContacts(ExternalUser requestingUser, String userIdentifier);
    
    /**
     * 
     * @param requestingUser
     * @param userIdentifier
     * @return
     */
    public Demographic getDemographics(ExternalUser requestingUser, String userIdentifier);
    
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
