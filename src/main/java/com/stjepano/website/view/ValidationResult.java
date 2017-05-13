package com.stjepano.website.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class ValidationResult {
    private final Map<String, String> errors = new HashMap<>();

    public void setError(String fieldName, String errorMessage) {
        this.errors.put(fieldName, errorMessage);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean hasError(String fieldName) {
        return errors.containsKey(fieldName);
    }

    public String getError(String fieldName) {
        return errors.get(fieldName);
    }
}
