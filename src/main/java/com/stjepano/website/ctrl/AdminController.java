package com.stjepano.website.ctrl;

import com.stjepano.website.components.AdminConfig;
import com.stjepano.website.components.AdminMenuPages;
import com.stjepano.website.components.DevUtils;
import com.stjepano.website.model.WebUser;
import com.stjepano.website.repository.WebUserRepository;
import com.stjepano.website.services.WebUserService;
import com.stjepano.website.view.AdminPage;
import com.stjepano.website.utils.UrlUtils;
import com.stjepano.website.view.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * This is administration controller.
 */
@Controller
@RequestMapping(AdminController.ADMIN_BASE_PATH)
public class AdminController {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private AdminMenuPages adminMenuPages;

    @Autowired
    private WebUserService webUserService;

    @Autowired
    private DevUtils devUtils;

    public static final String ADMIN_BASE_PATH = "/admin";
    public static final String DASHBOARD = "/";
    public static final String POSTS = "/posts";
    public static final String TAXONOMY = "/taxonomy";
    public static final String COMMENTS = "/comments";
    public static final String USERS = "/users";
    public static final String USERS_EDIT = "/users/{id}";
    public static final String USERS_CREATE = "/users/create";
    public static final String STATISTICS = "/statistics";
    public static final String SETTINGS = "/settings";

    private final String dashboardPath = UrlUtils.adminPath(DASHBOARD);
    private final String postsPath = UrlUtils.adminPath(POSTS);
    private final String taxonomyPath = UrlUtils.adminPath(TAXONOMY);
    private final String commentsPath = UrlUtils.adminPath(COMMENTS);
    private final String usersPath = UrlUtils.adminPath(USERS);
    private final String usersCreatePath = UrlUtils.adminPath(USERS_CREATE);
    private final String usersEditPath = UrlUtils.adminPath(USERS_EDIT);
    private final String statsPath = UrlUtils.adminPath(STATISTICS);
    private final String settingsPath = UrlUtils.adminPath(SETTINGS);


    private Menu createAdminMenu(String path) {
        return adminMenuPages.getMenu(path);
    }

    private void injectCommonModel(AdminPage adminPage, Model model) {
        WebUser webUser = new WebUser();
        webUser.setId(1000L);
        webUser.setDisplayName("Stjepan Obrankovic");
        webUser.setImageUri("/adminlte/img/user2-160x160.jpg");
        webUser.setCreated(new Date());

        model.addAttribute("menu", createAdminMenu(adminPage.getPath()));
        model.addAttribute("page", adminPage);
        model.addAttribute("user", webUser);
        model.addAttribute("config", adminConfig);
    }

    @RequestMapping(path = AdminController.DASHBOARD, method = RequestMethod.GET)
    public String index(Model model) {
        AdminPage pageInfo = adminMenuPages.getByPath(dashboardPath);
        injectCommonModel(pageInfo, model);
        return "admin/index";
    }

    @RequestMapping(path = AdminController.POSTS, method = RequestMethod.GET)
    public String posts(Model model) {
        AdminPage pageInfo = adminMenuPages.getByPath(postsPath);
        injectCommonModel(pageInfo, model);
        return "admin/posts";
    }

    @RequestMapping(path = AdminController.TAXONOMY, method = RequestMethod.GET)
    public String taxonomy(Model model) {
        AdminPage pageInfo = adminMenuPages.getByPath(taxonomyPath);
        injectCommonModel(pageInfo, model);
        return "admin/taxonomy";
    }

    @RequestMapping(path = AdminController.COMMENTS, method = RequestMethod.GET)
    public String comments(Model model) {
        AdminPage pageInfo = adminMenuPages.getByPath(commentsPath);
        injectCommonModel(pageInfo, model);
        return "admin/comments";
    }

    @RequestMapping(path = AdminController.USERS, method = RequestMethod.GET)
    public String users(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        AdminPage pageInfo = adminMenuPages.getByPath(usersPath);
        injectCommonModel(pageInfo, model);

        // now we need to load all the users and pass it in
        final Page<WebUser> usersPage = webUserService.getPage(page-1);
        model.addAttribute("users", devUtils.createRandomUsers(50, page, 7));

        return "admin/users";
    }

    @RequestMapping(path = AdminController.USERS_CREATE, method = RequestMethod.GET)
    public String createUser(Model model) {
        AdminPage pageInfo = new AdminPage(UrlUtils.adminPath(AdminController.USERS_CREATE), "Create user", "Create user", "fa-users", null);
        injectCommonModel(pageInfo, model);

        return "admin/users-create";
    }

    @RequestMapping(path = AdminController.USERS_EDIT, method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
        AdminPage pageInfo = new AdminPage(UrlUtils.adminPath(AdminController.USERS_EDIT), "Edit user", "Edit user", "fa-users", null);
        injectCommonModel(pageInfo, model);

        return "admin/users-edit";
    }

    @RequestMapping(path = AdminController.STATISTICS, method = RequestMethod.GET)
    public String stats(Model model) {
        AdminPage pageInfo = adminMenuPages.getByPath(statsPath);
        injectCommonModel(pageInfo, model);
        return "admin/stats";
    }

    @RequestMapping(path = AdminController.SETTINGS, method = RequestMethod.GET)
    public String settings(Model model) {
        AdminPage pageInfo = adminMenuPages.getByPath(settingsPath);
        injectCommonModel(pageInfo, model);
        return "admin/settings";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(Model model) {
        return "admin/login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "redirect:/";
    }

}
