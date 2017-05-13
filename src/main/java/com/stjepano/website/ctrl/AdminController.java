package com.stjepano.website.ctrl;

import com.stjepano.website.components.AdminConfig;
import com.stjepano.website.components.AdminPages;
import com.stjepano.website.components.DevUtils;
import com.stjepano.website.model.WebUser;
import com.stjepano.website.services.WebUserService;
import com.stjepano.website.utils.UrlUtils;
import com.stjepano.website.view.AdminPage;
import com.stjepano.website.view.Message;
import com.stjepano.website.view.UserDto;
import com.stjepano.website.view.ValidationResult;
import com.stjepano.website.view.validator.UserDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is administration controller.
 */
@Controller
@RequestMapping(AdminController.ADMIN_BASE_PATH)
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private AdminPages adminPages;

    @Autowired
    private WebUserService webUserService;

    @Autowired
    private DevUtils devUtils;

    @Autowired
    private UserDtoValidator userDtoValidator;

    public static final String ADMIN_BASE_PATH = "/admin";
    public static final String DASHBOARD = "/";
    public static final String POSTS = "/posts";
    public static final String TAXONOMY = "/taxonomy";
    public static final String COMMENTS = "/comments";
    public static final String USERS = "/users";
    public static final String STATISTICS = "/statistics";
    public static final String SETTINGS = "/settings";

    public static final String USERS_CREATE = "/users/create";
    public static final String USERS_EDIT = "/users/{id}";

    private static final String MESSAGE = "message";
    private static final String FLASH_MESSAGE = MESSAGE;


    // we are injecting common model attributes here
    // note that this method can take anything as @RequestMapping method can
    @ModelAttribute
    public void injectCommonModelAttributes(Model model, WebRequest webRequest) {
        final String path = (String) webRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, WebRequest.SCOPE_REQUEST);
        final String matchedPathPattern = (String) webRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, WebRequest.SCOPE_REQUEST);

        WebUser webUser = new WebUser();
        webUser.setId(1000L);
        webUser.setDisplayName("Stjepan Obrankovic");
        webUser.setImageUri("/adminlte/img/user2-160x160.jpg");
        webUser.setCreated(new Date());

        adminPages.getByPathPattern(matchedPathPattern).ifPresent( page -> {
            // render the menu to the client
            model.addAttribute("menu", adminPages.createMenu(path));
            // render page information (title, icon, description, etc ...)
            model.addAttribute("page", page);
            // render user information
            model.addAttribute("user", webUser);
            // render configuration ...
            model.addAttribute("config", adminConfig);
        });

    }

    @RequestMapping(path = AdminController.DASHBOARD, method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/index";
    }

    @RequestMapping(path = AdminController.POSTS, method = RequestMethod.GET)
    public String posts(Model model) {
        return "admin/posts";
    }

    @RequestMapping(path = AdminController.TAXONOMY, method = RequestMethod.GET)
    public String taxonomy(Model model) {
        return "admin/taxonomy";
    }

    @RequestMapping(path = AdminController.COMMENTS, method = RequestMethod.GET)
    public String comments(Model model) {
        return "admin/comments";
    }

    @RequestMapping(path = AdminController.USERS, method = RequestMethod.GET)
    public String users(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        // we need to load all the users and pass it in
        final Page<WebUser> usersPage = webUserService.getPage(page-1);
        model.addAttribute("users", usersPage);

        return "admin/users";
    }

    @RequestMapping(path = USERS_CREATE, method = RequestMethod.GET)
    public String createUser(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("dto", userDto);
        return "admin/users-create";
    }

    @RequestMapping(path = USERS_CREATE, method = RequestMethod.POST)
    public String handleCreateUser(@ModelAttribute("dto") UserDto userDto, RedirectAttributes redirectAttributes, Model model) {
        ValidationResult validationResult = userDtoValidator.validate(userDto);
        if (!validationResult.hasErrors()) {
            if (!userDto.isStayOnThisPage()) {
                redirectAttributes.addFlashAttribute(FLASH_MESSAGE, Message.success("User "+userDto.getEmail()+" successfully created"));
                return "redirect:/admin/users";
            } else {
                model.addAttribute(MESSAGE, Message.success("User "+userDto.getEmail()+" successfully created"));
                return "admin/users-create";
            }
        } else {
            model.addAttribute(MESSAGE, Message.warning("Validation errors, please reenter the data in the form and try again."));
            model.addAttribute("validation", validationResult);
            return "admin/users-create";
        }
    }

    @RequestMapping(path = USERS_EDIT, method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
        return "admin/users-edit";
    }

    @RequestMapping(path = "/users/delete", method = RequestMethod.POST)
    public String handleDeleteUsers(@RequestParam("users[]") String[] users, RedirectAttributes redirectAttributes) {
        List<Long> ids = Arrays.stream(users).map(Long::parseLong).collect(Collectors.toList());
        webUserService.deleteUsers(ids);
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, Message.info("Deleted "+users.length+" users"));
        return "redirect:" + UrlUtils.adminPath(USERS);
    }

    @RequestMapping(path = AdminController.STATISTICS, method = RequestMethod.GET)
    public String stats(Model model) {
        return "admin/stats";
    }

    @RequestMapping(path = AdminController.SETTINGS, method = RequestMethod.GET)
    public String settings(Model model) {
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
