package com.stjepano.website.ctrl;

import com.stjepano.website.components.WebsiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is the blog controller
 */
@Controller
public class BlogController {

    @Autowired
    private WebsiteConfig websiteConfig;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("config", websiteConfig);
        model.addAttribute("title", "Stjepan Obrankovic");
        model.addAttribute("subtitle", "A software developer");
        return "index";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("config", websiteConfig);
        model.addAttribute("title", "About");
        model.addAttribute("subtitle", "Something about me");
        return "about";
    }

    @RequestMapping("/tag/{tagTypeUri}/{tagUri}")
    public String tag(@PathVariable("tagTypeUri") String tagTypeUri, @PathVariable("tagUri") String tagUri, Model model) {
        model.addAttribute("config", websiteConfig);
        model.addAttribute("title", "Tag");
        return "index";
    }

    @RequestMapping("/category/{categoryUri}")
    public String category(@PathVariable("categoryUri") String categoryUri, Model model) {
        model.addAttribute("config", websiteConfig);
        model.addAttribute("title", "Category");
        return "index";
    }

    @RequestMapping("/{postUri}")
    public String post(@PathVariable("postUri") String postUri, Model model) {
        model.addAttribute("config", websiteConfig);
        model.addAttribute("title", "Lorem ipsum dolor");
        model.addAttribute("subtitle", "Lorem ipsum dolor sit amet");
        return "post";
    }
}
