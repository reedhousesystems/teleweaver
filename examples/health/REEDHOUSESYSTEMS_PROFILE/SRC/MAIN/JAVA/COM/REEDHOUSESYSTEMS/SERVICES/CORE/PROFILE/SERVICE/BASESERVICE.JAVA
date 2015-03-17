package com.reedhousesystems.services.core.profile.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.reedhousesystems.services.core.profile.exception.ValidationException;
import com.reedhousesystems.services.core.profile.model.ExternalUser;
import com.reedhousesystems.services.core.profile.model.Target;

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
		if (targetMatch == true && target.getAddress().getProvince() != null && target.getAddress().getProvince() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getProvince().equals(target.getAddress().getProvince())) targetMatch = false;
		}
		if (targetMatch == true && target.getAddress().getDistrict() != null && target.getAddress().getDistrict() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getDistrict().equals(target.getAddress().getDistrict())) targetMatch = false;
		}
		if (targetMatch == true && target.getAddress().getMunicipality() != null && target.getAddress().getMunicipality() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getMunicipality().equals(target.getAddress().getMunicipality())) targetMatch = false;
		}
		if (targetMatch == true && target.getAddress().getVillage() != null && target.getAddress().getVillage() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getVillage().equals(target.getAddress().getVillage())) targetMatch = false;
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
