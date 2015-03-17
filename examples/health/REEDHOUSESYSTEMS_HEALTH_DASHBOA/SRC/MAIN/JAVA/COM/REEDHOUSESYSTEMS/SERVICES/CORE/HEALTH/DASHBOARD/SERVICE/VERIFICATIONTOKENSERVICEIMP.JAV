package com.reedhousesystems.services.core.health.dashboard.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reedhousesystems.services.core.health.dashboard.api.UserDao;
import com.reedhousesystems.services.core.health.dashboard.api.VerificationTokenDao;
import com.reedhousesystems.services.core.health.dashboard.api.VerificationTokenService;
import com.reedhousesystems.services.core.health.dashboard.config.ApplicationConfig;
import com.reedhousesystems.services.core.health.dashboard.exception.AlreadyVerifiedException;
import com.reedhousesystems.services.core.health.dashboard.exception.AuthenticationException;
import com.reedhousesystems.services.core.health.dashboard.exception.TokenHasExpiredException;
import com.reedhousesystems.services.core.health.dashboard.exception.TokenNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Role;
import com.reedhousesystems.services.core.health.dashboard.model.User;
import com.reedhousesystems.services.core.health.dashboard.model.VerificationToken;
import com.reedhousesystems.services.core.health.dashboard.requests.LostPasswordRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.PasswordRequest;

@Service("verificationTokenService")
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenDao verificationTokenDao;

    private UserDao userDao;

    ApplicationConfig config;

    @Autowired
    public void setConfig(ApplicationConfig config) {
        this.config = config;
    }

    public ApplicationConfig getConfig() {
        return this.config;
    }

    @Autowired
    public VerificationTokenServiceImpl(UserDao userDao, VerificationTokenDao verificationTokenDao) {
        this.userDao = userDao;
        this.verificationTokenDao = verificationTokenDao;
    }

    @Transactional
    public VerificationToken sendEmailVerificationToken(String uuid) {
        User user = userDao.getUserByUUId(uuid);
        return sendEmailVerificationToken(user);
    }

    private VerificationToken sendEmailVerificationToken(User user) {
        VerificationToken token = new VerificationToken(user, VerificationToken.VerificationTokenType.emailVerification,
                config.getEmailVerificationTokenExpiryTimeInMinutes());
        user.addVerificationToken(token);
        userDao.saveOrUpdateUser(user);
        return token;
    }

    @Transactional
    public VerificationToken sendEmailRegistrationToken(String uuid) {
        User user = userDao.getUserByUUId(uuid);
        VerificationToken token = new VerificationToken(user,
                VerificationToken.VerificationTokenType.emailRegistration,
                config.getEmailRegistrationTokenExpiryTimeInMinutes());
        user.addVerificationToken(token);
        userDao.saveOrUpdateUser(user);
        return token;
    }

    /**
     * generate token if user found otherwise do nothing
     *
     * @param lostPasswordRequest
     * @return  a token or null if user not found
     */
    @Transactional
    public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest) {
        VerificationToken token = null;
        User user = userDao.getUserByUserName(lostPasswordRequest.getUserName());
        if (user != null) {
            token = user.getActiveLostPasswordToken();
            if (token == null) {
                token = new VerificationToken(user, VerificationToken.VerificationTokenType.lostPassword,
                        config.getLostPasswordTokenExpiryTimeInMinutes());
                user.addVerificationToken(token);
                userDao.saveOrUpdateUser(user);
            }
        }

        return token;
    }

    @Transactional
    public VerificationToken verify(String base64EncodedToken) {
        VerificationToken token = loadToken(base64EncodedToken);
        if (token.isVerified() || token.getUser().isVerified()) {
            throw new AlreadyVerifiedException();
        }
        token.setVerified(true);
        token.getUser().setVerified(true);
        userDao.saveOrUpdateUser(token.getUser());
        return token;
    }

    @Transactional
    public VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest) {
        Assert.notNull(base64EncodedToken);
        VerificationToken token = loadToken(base64EncodedToken);
        if (token.isVerified()) {
            throw new AlreadyVerifiedException();
        }
        token.setVerified(true);
        User user = token.getUser();
        try {
            user.setPassword(user.hashPassword(passwordRequest.getPassword()));
        } catch (Exception e) {
            throw new AuthenticationException();
        }
        //set user to verified if not already and authenticated role
        user.setVerified(true);
        if (user.hasRole(Role.anonymous)) {
            user.setRole(Role.authenticated);
        }
        userDao.saveOrUpdateUser(user);
        return token;
    }

    private VerificationToken loadToken(String base64EncodedToken) {
        Assert.notNull(base64EncodedToken);
        String rawToken = new String(Base64.decodeBase64(base64EncodedToken));
        VerificationToken token = verificationTokenDao.getVerificationTokenByToken(rawToken);
        if (token == null) {
            throw new TokenNotFoundException();
        }
        if (token.hasExpired()) {
            throw new TokenHasExpiredException();
        }
        return token;
    }
}
