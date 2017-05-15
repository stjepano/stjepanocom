package com.stjepano.website.view.validator;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
class Common {

    static final String PASSWORD_REGEX = "^[^ ]{4,}$";
    static final String DISPLAYNAME_REGEX = "^[a-zA-Z0-9_]{3}[a-zA-Z0-9_ ]*$";

    static boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    static boolean validateDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) return true;
        return displayName.matches(DISPLAYNAME_REGEX);
    }
}
