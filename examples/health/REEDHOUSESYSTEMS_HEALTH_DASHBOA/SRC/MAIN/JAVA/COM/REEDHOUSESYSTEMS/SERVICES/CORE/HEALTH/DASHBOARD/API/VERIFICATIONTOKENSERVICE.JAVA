package com.reedhousesystems.services.core.health.dashboard.api;

import com.reedhousesystems.services.core.health.dashboard.model.VerificationToken;
import com.reedhousesystems.services.core.health.dashboard.requests.LostPasswordRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.PasswordRequest;

public interface VerificationTokenService {

    public VerificationToken sendEmailVerificationToken(String userId);

    public VerificationToken sendEmailRegistrationToken(String userId);

    public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest);

    public VerificationToken verify(String base64EncodedToken);

    public VerificationToken resetPassword(String userId, PasswordRequest passwordRequest);
}
