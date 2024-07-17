package edu.xmu.blogsystem.controller.blog;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MyBlogController {
    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        //request.setAttribute("blogPageResult", blogPageResult);
        //request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        //request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        //request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("pageName", "首页");
        Map<String, String> configurations = new HashMap<>();
        request.setAttribute("configurations", configurations);
        return "blog/index";
    }
}
