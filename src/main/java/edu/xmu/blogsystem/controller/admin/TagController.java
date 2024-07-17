package edu.xmu.blogsystem.controller.admin;

import edu.xmu.blogsystem.service.TagService;
import edu.xmu.blogsystem.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tagPage(HttpServletRequest request) {
        request.setAttribute("path", "tags");
        return "admin/tag";
    }

    @GetMapping("/tags/list")
    @ResponseBody
    public Result list(Integer page, Integer limit) {
        if (ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(limit)) {
            return Result.genFailResult("参数异常！");
        }
        return Result.genSuccessResult(tagService.getBlogTagPage(page, limit));
    }


    @PostMapping("/tags/save")
    @ResponseBody
    public Result save(@RequestParam("tagName") String tagName) {
        if (!StringUtils.hasText(tagName)) {
            return Result.genFailResult("参数异常！");
        }
        if (tagService.saveTag(tagName)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("标签名称重复");
        }
    }

    @PostMapping("/tags/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return Result.genFailResult("参数异常！");
        }
        if (tagService.deleteBatch(ids)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("有关联数据请勿强行删除");
        }
    }
}
