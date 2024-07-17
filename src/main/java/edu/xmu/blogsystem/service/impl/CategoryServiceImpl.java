package edu.xmu.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xmu.blogsystem.dao.BlogCategoryMapper;
import edu.xmu.blogsystem.dao.BlogMapper;
import edu.xmu.blogsystem.entity.BlogCategory;
import edu.xmu.blogsystem.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    BlogCategoryMapper blogCategoryMapper;
    @Resource
    BlogMapper blogMapper;
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

    @Override
    public Boolean saveCategory(String categoryName) {
        // 查询分类是否已经存在
        BlogCategory temp = blogCategoryMapper.selectByCategoryName(categoryName);
        if (temp == null) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setCategoryName(categoryName);
            return blogCategoryMapper.insert(blogCategory) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateCategory(Integer categoryId, String categoryName) {
        BlogCategory blogCategory = blogCategoryMapper.selectById(categoryId);
        if (blogCategory != null) {
            blogCategory.setCategoryName(categoryName);
            //修改分类实体
            blogMapper.updateBlogCategories(categoryName, blogCategory.getCategoryId(), new Integer[]{categoryId});
            return blogCategoryMapper.updateById(blogCategory) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) return false;
        //修改blog表
        blogMapper.updateBlogCategories("默认分类", 0, ids);
        //删除分类数据
        return blogCategoryMapper.deleteBatch(ids) > 0;
    }
}
