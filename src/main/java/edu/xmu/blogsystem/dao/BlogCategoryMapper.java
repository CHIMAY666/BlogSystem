package edu.xmu.blogsystem.dao;

import edu.xmu.blogsystem.entity.BlogCategory;

import java.util.List;

public interface BlogCategoryMapper{
    List<BlogCategory> getAllCategories();
    int deleteBatch(Integer[] ids);
    int insert(BlogCategory record);
    BlogCategory selectById(Integer categoryId);
    BlogCategory selectByCategoryName(String categoryName);
    int updateById(BlogCategory record);
}