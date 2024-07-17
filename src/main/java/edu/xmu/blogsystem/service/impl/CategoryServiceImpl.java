package edu.xmu.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xmu.blogsystem.dao.BlogCategoryMapper;
import edu.xmu.blogsystem.entity.BlogCategory;
import edu.xmu.blogsystem.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    BlogCategoryMapper blogCategoryMapper;
    @Override
    public Map<String, Object> getBlogCategoryPage(Map<String, Object> params) {
        Integer page = (Integer) params.get("page");
        Integer limit = (Integer) params.get("limit");
        PageHelper.startPage(page, limit);
        List<BlogCategory> list = blogCategoryMapper.getAllCategories();
        PageInfo<BlogCategory> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", pageInfo.getTotal());
        return result;
    }

    @Override
    public Map<String, Object> getAllCategories() {
        List<BlogCategory> list = blogCategoryMapper.getAllCategories();
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", list.size());
        return result;
    }
}
