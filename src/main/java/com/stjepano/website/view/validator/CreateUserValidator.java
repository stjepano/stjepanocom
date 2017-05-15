package com.stjepano.website.view.validator;

import com.stjepano.website.view.UserDto;
import com.stjepano.website.view.ValidationResult;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

/**
 * {@link UserDto} validator ...
 */
@Component
public class CreateUserValidator implements Validator<UserDto> {

    @Override
    public ValidationResult validate(UserDto userDto) {
        final ValidationResult validationResult = new ValidationResult();
        if (!EmailValidator.getInstance(true).isValid(userDto.getEmail())) {
            validationResult.setError("email", "Email is not valid!");
        }
        if (!Common.validatePassword(userDto.getPassword())) {
            validationResult.setError("password", "Password is not valid, should be minimum 4 characters and must not contain spaces!");
        }
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            validationResult.setError("passwordConfirm", "Passwords do not match!");
        }
        if (!Common.validateDisplayName(userDto.getDisplayName())) {
            validationResult.setError("displayName", "Display name should have at least 3 characters non space characters.");
        }

        return validationResult;
    }

}
