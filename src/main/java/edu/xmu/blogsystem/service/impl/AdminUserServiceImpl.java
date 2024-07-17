package edu.xmu.blogsystem.service.impl;

import edu.xmu.blogsystem.dao.AdminUserMapper;
import edu.xmu.blogsystem.entity.AdminUser;
import edu.xmu.blogsystem.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Override
    public AdminUser login(String userName, String password) {
        return adminUserMapper.login(userName, password);
    }
    @Override
    public Boolean updatePassword(Integer userId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(userId);
        //当前用户非空才可以进行更改
        if (adminUser != null && originalPassword.equals(adminUser.getPassword())) {
            adminUser.setPassword(newPassword);
            return adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0;
        }
        return false;
    }

    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //修改信息
            adminUser.setUserName(loginUserName);
            adminUser.setNickName(nickName);
            //修改成功则返回true
            return adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0;
        }
        return false;
    }
}
