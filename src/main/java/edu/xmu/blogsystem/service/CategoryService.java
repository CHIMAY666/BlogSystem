package edu.xmu.blogsystem.service;

import edu.xmu.blogsystem.entity.BlogCategory;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    /**
     * 查询分类的分页数据
     *
     * @param params 分页参数
     * @return 分页数据
     */
    Map<String, Object> getBlogCategoryPage(Map<String, Object> params);
    Map<String, Object> getAllCategories();
}
