package edu.xmu.blogsystem.controller.admin;

import edu.xmu.blogsystem.service.CategoryService;
import edu.xmu.blogsystem.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 跳转到分类管理页面
     */
    @GetMapping("/categories")
    public String categoryPage(HttpServletRequest request) {
        request.setAttribute("path", "categories");
        return "admin/category";
    }

    /**
     * 分页查询分类列表
     * @param params 参数：[page: 当前页码] [limit: 每页大小]
     * @return 分类列表和大小
     */
    @RequestMapping(value = "/categories/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            // 不满足分页参数的要求，返回整个分类列表
            return Result.genSuccessResult(categoryService.getAllCategories());
        }
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        return Result.genSuccessResult(categoryService.getBlogCategoryPage(page, limit));
    }

    /**
     * 分类保存
     */
    @RequestMapping(value = "/categories/save")
    @ResponseBody
    public Result save(@RequestParam("categoryName") String categoryName) {
        if (!StringUtils.hasText(categoryName)) {
            return Result.genFailResult("请输入分类名称！");
        }
        if (categoryService.saveCategory(categoryName)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("分类名称重复");
        }
    }
    /**
     * 分类修改
     */
    @RequestMapping(value = "/categories/update")
    @ResponseBody
    public Result update(@RequestParam("categoryId") Integer categoryId,
                         @RequestParam("categoryName") String categoryName) {
        if (!StringUtils.hasText(categoryName)) {
            return Result.genFailResult("请输入分类名称！");
        }
        if (categoryService.updateCategory(categoryId, categoryName)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("分类名称重复");
        }
    }
    /**
     * 分类删除
     */
    @RequestMapping(value = "/categories/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return Result.genFailResult("参数异常！");
        }
        if (categoryService.deleteBatch(ids)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("删除失败");
        }
    }
}
