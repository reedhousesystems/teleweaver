package com.reedhousesystems.services.core.health.messenger.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.reedhousesystems.services.core.health.messenger.exception.ValidationException;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.model.Target;

public abstract class BaseService {

    private Validator validator;
    
    public BaseService() {
    	
    }

    public BaseService(Validator validator) {
        this.validator = validator;
    }

    protected void validate(Object request) {
        Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            throw new ValidationException(constraintViolations);
        }
    }
	
	protected boolean targetMatch(ExternalUser userMakingRequest, Target target) {
		boolean targetMatch = true;
		if (targetMatch == true && target.getLanguage() != null && target.getLanguage() != "") {
			if (!userMakingRequest.getDemographic().getLanguage().equals(target.getLanguage())) targetMatch = false;
		}
		if (targetMatch == true && target.getLocation().getProvince() != null && target.getLocation().getProvince() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getProvince().equals(target.getLocation().getProvince())) targetMatch = false;
		}
		if (targetMatch == true && target.getLocation().getDistrict() != null && target.getLocation().getDistrict() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getDistrict().equals(target.getLocation().getDistrict())) targetMatch = false;
		}
		if (targetMatch == true && target.getLocation().getMunicipality() != null && target.getLocation().getMunicipality() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getMunicipality().equals(target.getLocation().getMunicipality())) targetMatch = false;
		}
		if (targetMatch == true && target.getLocation().getVillage() != null && target.getLocation().getVillage() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getVillage().equals(target.getLocation().getVillage())) targetMatch = false;
		}
		if (targetMatch == true && target.getDateOfBirthFrom() != null) {
			if (userMakingRequest.getDemographic().getDateOfBirth().compareTo(target.getDateOfBirthFrom()) <= 0) targetMatch = false;
		}
		if (targetMatch == true && target.getDateOfBirthTo() != null) {
			if (userMakingRequest.getDemographic().getDateOfBirth().compareTo(target.getDateOfBirthTo()) >= 0) targetMatch = false;
		}
		if (targetMatch == true && target.getMaritalStatus() != null && target.getMaritalStatus() != "") {
			if (!userMakingRequest.getDemographic().getMaritalStatus().equals(target.getMaritalStatus())) targetMatch = false;
		}
		if (targetMatch == true && target.getOccupation() != null && target.getOccupation() != "") {
			if (!userMakingRequest.getDemographic().getOccupation().equals(target.getOccupation())) targetMatch = false;
		}
		if (targetMatch == true && target.getGender() != null && target.getGender() != "") {
			if (!userMakingRequest.getDemographic().getGender().equals(target.getGender())) targetMatch = false;
		}
		if (targetMatch == true && target.getSexOrientation() != null && target.getSexOrientation() != "") {
			if (!userMakingRequest.getDemographic().getSexOrientation().equals(target.getSexOrientation())) targetMatch = false;
		}
		return targetMatch;
		
	}

}
