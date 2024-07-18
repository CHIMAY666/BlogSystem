package edu.xmu.blogsystem.service;

import edu.xmu.blogsystem.controller.vo.BlogDetailVO;
import edu.xmu.blogsystem.controller.vo.SimpleBlogListVO;
import edu.xmu.blogsystem.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Map<String, Object> getAllBlogs();
    Map<String, Object> getBlogsPage(Integer page, Integer limit);
    String saveBlog(Blog blog);
    BlogDetailVO getBlogDetail(Integer id);
    Boolean deleteBatch(Integer[] ids);
    Integer getTotalBlogs();
    Blog getBlogById(Integer blogId);
    String updateBlog(Blog blog);
    Map<String, Object> getBlogsForIndexPage(int pageNum);
    /**
     * 首页侧边栏数据列表
     * 0-点击最多 1-最新发布
     */
    List<SimpleBlogListVO> getBlogListForIndexPage(int type);
}
