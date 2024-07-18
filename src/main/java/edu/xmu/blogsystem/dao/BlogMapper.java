package edu.xmu.blogsystem.dao;

import edu.xmu.blogsystem.entity.Blog;
import edu.xmu.blogsystem.entity.BlogCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    List<Blog> getAllBlogs();
    Blog selectById(Integer blogId);

    void updateBlogCategories(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids")Integer[] ids);

    Integer getTotalBlogs(Map<String, Object> map);

    Integer deleteBatch(Integer[] ids);

    Integer insert(Blog blog);

    Integer updateById(Blog blogForUpdate);

    List<Blog> findBlogList(Map<String, Object> params);

    List<Blog> findBlogListByType(@Param("type") int type, @Param("limit") int limit);
}