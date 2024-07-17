package edu.xmu.blogsystem.dao;

import edu.xmu.blogsystem.entity.BlogCategory;

import java.util.List;

public interface BlogCategoryMapper{
    List<BlogCategory> getAllCategories();
}