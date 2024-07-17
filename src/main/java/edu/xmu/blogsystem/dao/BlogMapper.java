package edu.xmu.blogsystem.dao;

import edu.xmu.blogsystem.entity.BlogCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    void updateBlogCategories(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids")Integer[] ids);
}