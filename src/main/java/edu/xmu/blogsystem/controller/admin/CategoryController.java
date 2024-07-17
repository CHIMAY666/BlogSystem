package edu.xmu.blogsystem.controller.admin;

import edu.xmu.blogsystem.service.CategoryService;
import edu.xmu.blogsystem.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Resource
    CategoryService categoryService;

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
        Map<String, Object> pageParams = new HashMap<>();
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        pageParams.put("page", page);
        pageParams.put("limit", limit);
        return Result.genSuccessResult(categoryService.getBlogCategoryPage(pageParams));
    }
}
