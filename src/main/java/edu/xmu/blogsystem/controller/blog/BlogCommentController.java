package edu.xmu.blogsystem.controller.blog;

import cn.hutool.captcha.ShearCaptcha;
import edu.xmu.blogsystem.entity.BlogComment;
import edu.xmu.blogsystem.service.CommentService;
import edu.xmu.blogsystem.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogCommentController {
    @Autowired
    private CommentService blogCommentService;
    @PostMapping(value = "/blog/comment")
    @ResponseBody
    public Result comment(HttpServletRequest request, HttpSession session, @RequestParam Integer blogId,
                          @RequestParam String verifyCode,
                          @RequestParam String commentator,
                          @RequestParam String commentBody){
        if(!StringUtils.hasText(verifyCode)){
            return Result.genFailResult("验证码不能为空");
        }
        ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("verifyCode");
        if(shearCaptcha == null || !shearCaptcha.verify(verifyCode)){
            return Result.genFailResult("验证码错误");
        }
        if (!StringUtils.hasText(commentator)) {
            return Result.genFailResult("请输入称呼");
        }
        if (!StringUtils.hasText(commentBody)) {
            return Result.genFailResult("请输入评论内容");
        }
        if (commentBody.trim().length() > 200) {
            return Result.genFailResult("评论内容过长");
        }
        BlogComment comment = new BlogComment();
        comment.setBlogId(blogId);
        comment.setCommentator(commentator);
        comment.setCommentBody(commentBody);
        comment.setCommentStatus(false);
        comment.setIsDeleted(false);
        if(blogCommentService.addComment(comment)) return Result.genSuccessResult();
        else return Result.genFailResult("评论发布失败！");
    }
}
