package com.reedhousesystems.services.core.profile.api;

import com.reedhousesystems.services.core.profile.model.VerificationToken;
import com.reedhousesystems.services.core.profile.request.LostPasswordRequest;
import com.reedhousesystems.services.core.profile.request.PasswordRequest;

public interface VerificationTokenService {

    public VerificationToken sendEmailVerificationToken(String userId);

    public VerificationToken sendEmailRegistrationToken(String userId);

    public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest);

    public VerificationToken verify(String base64EncodedToken);

    public VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest);
}
