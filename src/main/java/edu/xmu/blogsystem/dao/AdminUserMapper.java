package edu.xmu.blogsystem.dao;

import edu.xmu.blogsystem.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminUserMapper {
    @Select("select * from admin_user where user_name=#{userName} and password=#{password}")
    AdminUser login(@Param("userName") String userName, @Param("password") String password);
    @Select("select * from admin_user where user_id=#{userId}")
    AdminUser selectByPrimaryKey(Integer userId);
    @Update("<script> update admin_user " +
            "<set>" +
            "<if test=\"userName!=null\">user_name=#{userName}, </if>" +
            "<if test=\"password!=null\">password=#{password}, </if>" +
            "<if test=\"nickName!=null\">nick_name=#{nickName}, </if>" +
            "</set>" +
            "where user_id=#{userId} </script>" )

    int updateByPrimaryKeySelective(AdminUser adminUser);
}
