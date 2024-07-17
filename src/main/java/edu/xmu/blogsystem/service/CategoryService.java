package edu.xmu.blogsystem.service;

import edu.xmu.blogsystem.entity.BlogCategory;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    /**
     * 查询分类的分页数据
     * @return 分页数据
     */
    Map<String, Object> getBlogCategoryPage(Integer page, Integer limit);
    Map<String, Object> getAllCategories();
    Boolean saveCategory(String categoryName);
    Boolean updateCategory(Integer categoryId, String categoryName);
    Boolean deleteBatch(Integer[] ids);
    int getTotalCategories();
}
