package edu.xmu.blogsystem.controller.admin;

import edu.xmu.blogsystem.entity.BlogComment;
import edu.xmu.blogsystem.service.CommentService;
import edu.xmu.blogsystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 分页查询评论列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/comments/list")
    @ResponseBody
    public Result list(Integer page, Integer limit) {
        if (page < 1 || limit < 1) {
            return Result.genSuccessResult(commentService.getCommentPageByBlogIdAndPageNum(null, 1, 10));
        }
        return Result.genSuccessResult(commentService.getCommentPageByBlogIdAndPageNum(null, page, limit));
    }

    /**
     * 批量审核评论
     * @param ids
     * @return
     */
    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result checkDone(@RequestBody Integer[] ids) {
        Map<String, Object> result = new HashMap<>();
        if (ids.length < 1) {
            return Result.genFailResult("参数异常！");
        }
        if (commentService.checkDone(ids)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("审核失败");
        }
    }

    /**
     * 回复评论
     * @param commentId
     * @param replyBody
     * @return
     */
    @PostMapping("/comments/reply")
    @ResponseBody
    public Result checkDone(@RequestParam("commentId") Integer commentId,
                            @RequestParam("replyBody") String replyBody) {
        if (commentId == null || commentId < 1 || !StringUtils.hasText(replyBody)) {
            return Result.genFailResult("参数异常！");
        }
        if (commentService.reply(commentId, replyBody)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("回复失败");
        }
    }

    /**
     * 批量删除评论
     * @param ids
     * @return
     */
    @PostMapping("/comments/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return Result.genFailResult("参数异常！");
        }
        if (commentService.deleteBatch(ids)) {
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("刪除失败");
        }
    }

    /**
     * 跳转到评论管理页面
     * @param request
     * @return
     */
    @GetMapping("/comments")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "comments");
        return "admin/comment";
    }
}
