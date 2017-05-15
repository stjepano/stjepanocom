package com.stjepano.website.view.validator;

import com.stjepano.website.view.UserDto;
import com.stjepano.website.view.ValidationResult;
import org.springframework.stereotype.Component;

/**
 * Used for update user validation
 */
@Component
public class UpdateUserValidator implements Validator<UserDto> {
    @Override
    public ValidationResult validate(UserDto userDto) {
        final ValidationResult validationResult = new ValidationResult();

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            // password change
            if (!Common.validatePassword(userDto.getPassword())) {
                validationResult.setError("password", "Password is not valid, should be minimum 4 characters and must not contain spaces!");
            }
            if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
                validationResult.setError("passwordConfirm", "Passwords do not match!");
            }
        }

        if (!Common.validateDisplayName(userDto.getDisplayName())) {
            validationResult.setError("displayName", "Display name should have at least 3 characters non space characters.");
        }

        return validationResult;
    }
}
