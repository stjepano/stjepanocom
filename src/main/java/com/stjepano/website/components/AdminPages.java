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
import java.util.Optional;

/**
 * This contains list of admin pages.
 *
 * You can create real menu from this one by calling createMenu method.
 */
@Component
public class AdminPages {

    private final List<AdminPage> menuPages = new ArrayList<>();
    private final List<AdminPage> otherPages = new ArrayList<>();

    @PostConstruct
    public void init() {
        // menu pages
        menuPages.add(new AdminPage(UrlUtils.adminPath(AdminController.DASHBOARD), "Dashboard", "What is new on the blog", "fa-tachometer", null));
        menuPages.add(new AdminPage(UrlUtils.adminPath(AdminController.POSTS), "Posts", "List of all the posts", "fa-file-o", null));
        menuPages.add(new AdminPage(UrlUtils.adminPath(AdminController.TAXONOMY), "Tags & Categories", "Create/edit/delete tags and categories", "fa-file-o", null));
        menuPages.add(new AdminPage(UrlUtils.adminPath(AdminController.COMMENTS), "Comments", "Manage comments", "fa-file-o", null));
        menuPages.add(new AdminPage(UrlUtils.adminPath(AdminController.USERS), "Users", "Manage users", "fa-users", null));
        menuPages.add(new AdminPage(UrlUtils.adminPath(AdminController.STATISTICS), "Statistics", "View site statistics", "fa-line-chart", null));
        menuPages.add(new AdminPage(UrlUtils.adminPath(AdminController.SETTINGS), "Settings", "Edit site settings", "fa-cog", null));
        // other administration pages
        otherPages.add(new AdminPage(UrlUtils.adminPath(AdminController.USERS_CREATE), "Create user", "Create user", "fa-users", null));
        otherPages.add(new AdminPage(UrlUtils.adminPath(AdminController.USERS_EDIT), "Edit user", "Edit user", "fa-users", null));
    }

    public Menu createMenu(String currentPath) {
        Menu menu = new Menu();
        for (AdminPage adminPage : menuPages) {
            Link link = new Link(adminPage.getPath(), adminPage.getTitle(), adminPage.getFaIcon(), currentPath.equals(adminPage.getPath()));
            menu.getLinks().add(link);
        }
        return menu;
    }

    public AdminPage getByPathPattern(String path) {
        Optional<AdminPage> menuOptional = menuPages.stream().filter(ap -> ap.getPath().equals(path)).findFirst();
        if (menuOptional.isPresent()) return menuOptional.get();
        return otherPages.stream().filter(ap -> ap.getPath().equals(path)).findFirst().orElseThrow(() -> new RuntimeException("Could not find admin page with path '"+path+"'"));
    }

}
