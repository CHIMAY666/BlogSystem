package edu.xmu.blogsystem.controller.blog;

import edu.xmu.blogsystem.controller.vo.BlogDetailVO;
import edu.xmu.blogsystem.service.BlogService;
import edu.xmu.blogsystem.service.CommentService;
import edu.xmu.blogsystem.service.TagService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MyBlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private TagService tagService;
    @Resource
    private CommentService commentService;
    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, 1);
    }

    /**
     * 首页 分页数据
     */
    @GetMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        Map<String, Object> blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        if (blogPageResult == null) {
            return "redirect:error/404";
        }
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("pageName", "首页");
        Map<String, Object> configurations = new HashMap<>();
        configurations.put("yourName", request.getSession().getAttribute("loginUser"));
        request.setAttribute("configurations", configurations);
        return "blog/index";
    }

    /**
     * 详情页
     */
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    public String detail(HttpServletRequest request, @PathVariable("blogId") Integer blogId, @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetail(blogId);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            Map<String, Object> commentPages = commentService.getCommentPageByBlogIdAndPageNum(blogId, commentPage, 8);
            request.setAttribute("commentPageResult", commentPages);
            //System.out.println(commentPages);
        }
        else return "redirect:error/404";
        request.setAttribute("pageName", "详情");
        Map<String, Object> configurations = new HashMap<>();
        configurations.put("yourName", request.getSession().getAttribute("loginUser"));
        request.setAttribute("configurations", configurations);
        return "blog/detail";
    }
}
