package com.stjepano.website.components;

import com.stjepano.website.ctrl.AdminController;
import com.stjepano.website.utils.UrlUtils;
import com.stjepano.website.view.AdminPage;
import com.stjepano.website.view.Link;
import com.stjepano.website.view.Menu;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * This contains list of pages that will be in admin menu ...
 *
 * You can create real menu from this one by calling getMenu method ...
 */
@Component
public class AdminMenuPages {

    private final List<AdminPage> adminPages = new ArrayList<>();

    @PostConstruct
    public void init() {
        adminPages.add(new AdminPage(UrlUtils.adminPath(AdminController.DASHBOARD), "Dashboard", "What is new on the blog", "fa-tachometer", null));
        adminPages.add(new AdminPage(UrlUtils.adminPath(AdminController.POSTS), "Posts", "List of all the posts", "fa-file-o", null));
        adminPages.add(new AdminPage(UrlUtils.adminPath(AdminController.TAXONOMY), "Tags & Categories", "Create/edit/delete tags and categories", "fa-file-o", null));
        adminPages.add(new AdminPage(UrlUtils.adminPath(AdminController.COMMENTS), "Comments", "Manage comments", "fa-file-o", null));
        adminPages.add(new AdminPage(UrlUtils.adminPath(AdminController.USERS), "Users", "Manage users", "fa-users", null));
        adminPages.add(new AdminPage(UrlUtils.adminPath(AdminController.STATISTICS), "Statistics", "View site statistics", "fa-line-chart", null));
        adminPages.add(new AdminPage(UrlUtils.adminPath(AdminController.SETTINGS), "Settings", "Edit site settings", "fa-cog", null));
    }

    public Menu getMenu(String currentPath) {
        Menu menu = new Menu();
        for (AdminPage adminPage : adminPages) {
            Link link = new Link(adminPage.getPath(), adminPage.getTitle(), adminPage.getFaIcon(), currentPath.equals(adminPage.getPath()));
            menu.getLinks().add(link);
        }
        return menu;
    }

    public AdminPage getByPath(String path) {
        return adminPages.stream().filter(ap -> ap.getPath().equals(path)).findFirst().orElseThrow(() -> new RuntimeException("Could not find admin page for '" + path + "'!"));
    }

}
