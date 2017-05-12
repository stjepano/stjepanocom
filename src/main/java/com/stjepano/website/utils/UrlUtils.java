package com.stjepano.website.utils;

import com.stjepano.website.ctrl.AdminController;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class UrlUtils {

    public static String adminPath(String subpath) {
        return AdminController.ADMIN_BASE_PATH + subpath;
    }

    public static String stripAdminBasePath(String path) {
        if (path.startsWith(AdminController.ADMIN_BASE_PATH)) {
            return path.substring(AdminController.ADMIN_BASE_PATH.length());
        }
        return path;
    }

}
