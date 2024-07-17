package edu.xmu.blogsystem.service;

import edu.xmu.blogsystem.entity.AdminUser;

public interface AdminUserService {
    AdminUser login(String userName, String password);
    AdminUser getUserById(Integer userId);
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);
}
