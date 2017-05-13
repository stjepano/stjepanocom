package com.stjepano.website.view.validator;

import com.stjepano.website.view.ValidationResult;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public interface Validator<T> {

    ValidationResult validate(T obj);

}
