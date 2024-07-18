package edu.xmu.blogsystem.controller.admin;

import edu.xmu.blogsystem.entity.Blog;
import edu.xmu.blogsystem.service.BlogService;
import edu.xmu.blogsystem.service.CategoryService;
import edu.xmu.blogsystem.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BlogController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    @GetMapping("/blogs")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "blogs");
        return "admin/blog";
    }

    @GetMapping("/blogs/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        request.setAttribute("categories", categoryService.getAllCategories().get("list"));
        return "admin/edit";
    }

    @GetMapping("/blogs/edit/{blogId}")
    public String edit(HttpServletRequest request, @PathVariable("blogId") Integer blogId) {
        request.setAttribute("path", "edit");
        Blog blog = blogService.getBlogById(blogId);
        if (blog == null) {
            return "redirect:/error/400";
        }
        request.setAttribute("blog", blog);
        request.setAttribute("categories", categoryService.getAllCategories().get("list"));
        return "admin/edit";
    }

    /**
     * 发布博客
     */
    @PostMapping("/blogs/save")
    @ResponseBody
    public Result save(@RequestParam("blogTitle") String blogTitle,
                       @RequestParam("blogCategoryId") Integer blogCategoryId,
                       @RequestParam("blogTags") String blogTags,
                       @RequestParam("blogContent") String blogContent,
                       @RequestParam("blogCoverImage") String blogCoverImage,
                       @RequestParam("blogStatus") Byte blogStatus,
                       @RequestParam("enableComment") Byte enableComment) {
        if (!StringUtils.hasText(blogTitle)) {
            return Result.genFailResult("请输入文章标题");
        }
        if (blogTitle.trim().length() > 150) {
            return Result.genFailResult("标题过长");
        }
        if (!StringUtils.hasText(blogTags)) {
            return Result.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return Result.genFailResult("标签过长");
        }
        if (!StringUtils.hasText(blogContent)) {
            return Result.genFailResult("请输入文章内容");
        }
        if (blogTags.trim().length() > 100000) {
            return Result.genFailResult("文章内容过长");
        }
        if (!StringUtils.hasText(blogCoverImage)) {
            return Result.genFailResult("封面图不能为空");
        }
        Blog blog = new Blog();
        blog.setBlogTitle(blogTitle);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String saveBlogResult = blogService.saveBlog(blog);
        if ("success".equals(saveBlogResult)) {
            return Result.genSuccessResult("添加成功");
        } else {
            return Result.genFailResult(saveBlogResult);
        }
    }

    /**
     * 更新博客
     */
    @PostMapping("/blogs/update")
    @ResponseBody
    public Result update(@RequestParam("blogId") Integer blogId,
                         @RequestParam("blogTitle") String blogTitle,
                         @RequestParam("blogCategoryId") Integer blogCategoryId,
                         @RequestParam("blogTags") String blogTags,
                         @RequestParam("blogContent") String blogContent,
                         @RequestParam("blogCoverImage") String blogCoverImage,
                         @RequestParam("blogStatus") Byte blogStatus,
                         @RequestParam("enableComment") Byte enableComment) {
        if (!StringUtils.hasText(blogTitle)) {
            return Result.genFailResult("请输入文章标题");
        }
        if (blogTitle.trim().length() > 150) {
            return Result.genFailResult("标题过长");
        }
        if (!StringUtils.hasText(blogTags)) {
            return Result.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return Result.genFailResult("标签过长");
        }
        if (!StringUtils.hasText(blogContent)) {
            return Result.genFailResult("请输入文章内容");
        }
        if (blogTags.trim().length() > 100000) {
            return Result.genFailResult("文章内容过长");
        }
        if (!StringUtils.hasText(blogCoverImage)) {
            return Result.genFailResult("封面图不能为空");
        }
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String updateBlogResult = blogService.updateBlog(blog);
        if ("success".equals(updateBlogResult)) {
            return Result.genSuccessResult("修改成功");
        } else {
            return Result.genFailResult(updateBlogResult);
        }
    }
    
    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            // 不满足分页参数的要求，返回整个分类列表
            return Result.genSuccessResult(blogService.getAllBlogs());
        }
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        return Result.genSuccessResult(blogService.getBlogsPage(page, limit));
    }

    @PostMapping("/blogs/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return Result.genFailResult("参数异常！");
        }
        if (blogService.deleteBatch(ids)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("删除失败");
        }
    }
}
