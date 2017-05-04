package com.stjepano.website.utils;

import com.stjepano.website.ctrl.AdminController;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class UrlUtils {

    public static String adminPath(String subpath) {
        return AdminController.ADMIN_BASE_PATH + subpath;
    }

}
