package edu.xmu.blogsystem.controller.admin;

import cn.hutool.captcha.ShearCaptcha;
import edu.xmu.blogsystem.dao.BlogTagMapper;
import edu.xmu.blogsystem.entity.AdminUser;
import edu.xmu.blogsystem.service.AdminUserService;
import edu.xmu.blogsystem.service.BlogService;
import edu.xmu.blogsystem.service.CategoryService;
import edu.xmu.blogsystem.service.CommentService;
import edu.xmu.blogsystem.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;
    @Resource
    private BlogTagMapper tagService;
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", categoryService.getTotalCategories());
        request.setAttribute("blogCount", blogService.getTotalBlogs());
        request.setAttribute("tagCount", tagService.getTotalTags());
        request.setAttribute("commentCount", commentService.getTotalComments());
        return "admin/index";
    }
    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    /**
     * 管理员登录
     * @param userName
     * @param password
     * @param verifyCode
     * @return
     */
    @PostMapping(value = "/login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        @RequestParam String verifyCode,
                        HttpSession session) {
        if (!StringUtils.hasText(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("verifyCode");
        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }
        AdminUser adminUser = adminUserService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getUserId());
            //session过期时间设置为7200秒 即两小时
            //session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "登陆失败");
            return "admin/login";
        }
    }
    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Object userIdObject = request.getSession().getAttribute("loginUserId");
        if (userIdObject == null) return "admin/login";
        Integer loginUserId = (Integer) userIdObject;
        AdminUser adminUser = adminUserService.getUserById(loginUserId);
        if (adminUser == null) return "admin/login";
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    /**
     * 修改密码
     * @param originalPassword 原密码
     * @param newPassword 新密码
     */
    @PostMapping("/profile/password")
    @ResponseBody
    public Result passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (!StringUtils.hasText(originalPassword) || !StringUtils.hasText(newPassword)) {
            return Result.genFailResult("参数不能为空");
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return Result.genSuccessResult();
        } else {
             return Result.genFailResult("修改失败");
        }
    }

    /**
     * 修改用户名、昵称
     * @param userName 用户名
     * @param nickName 昵称
     */
    @PostMapping("/profile/name")
    @ResponseBody
    public Result nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String userName,
                             @RequestParam("nickName") String nickName) {
        if (!StringUtils.hasText(userName) || !StringUtils.hasText(nickName)) {
            return Result.genFailResult("参数不能为空");
        }
        Integer userId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateName(userId, userName, nickName)) {
            request.setAttribute("nickName", nickName);
            return Result.genSuccessResult();
        } else {
            return Result.genFailResult("修改失败");
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }
}
