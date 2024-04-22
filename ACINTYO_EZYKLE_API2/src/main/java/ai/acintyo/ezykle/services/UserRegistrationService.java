package ai.acintyo.ezykle.services;


import java.util.List;

import org.springframework.data.domain.Pageable;

import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserRegistration;

public interface UserRegistrationService {

	EzUserRegistration saveRegistration(UserRegistrationForm registrationForm);

    List<EzUserRegistration> fetchAllUsers(Pageable pageable);
	
    EzUserRegistration fetchUserById(Integer id);
    
    EzUserRegistration UpdateUserById(Integer id,UserRegistrationForm userForm);
}
