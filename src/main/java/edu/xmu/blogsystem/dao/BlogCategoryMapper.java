package edu.xmu.blogsystem.dao;

import edu.xmu.blogsystem.entity.BlogCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCategoryMapper{
    List<BlogCategory> getAllCategories();
    int deleteBatch(Integer[] ids);
    int insert(BlogCategory record);
    BlogCategory selectById(Integer categoryId);
    List<BlogCategory> selectByIds(@Param("categoryIds") List<Integer> categoryIds);
    BlogCategory selectByCategoryName(String categoryName);
    int updateById(BlogCategory record);
    int getTotalCategories();
}