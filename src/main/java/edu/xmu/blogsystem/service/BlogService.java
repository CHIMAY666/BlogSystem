package edu.xmu.blogsystem.service;

import edu.xmu.blogsystem.entity.Blog;

import java.util.Map;

public interface BlogService {
    Map<String, Object> getAllBlogs();
    Map<String, Object> getBlogsPage(Integer page, Integer limit);
    String saveBlog(Blog blog);
    Boolean deleteBatch(Integer[] ids);
    Integer getTotalBlogs();
}
