package edu.xmu.blogsystem.entity;

import lombok.Data;

@Data
public class AdminUser {
    private Integer userId;
    private String userName;
    private String password;
    private String nickName;
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("userId: ").append(userId)
                .append("userName: ").append(userName)
                .append("password").append(password)
                .append("nickName: ").append(nickName);
        return sb.toString();
    }
}
