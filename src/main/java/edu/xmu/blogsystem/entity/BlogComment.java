package edu.xmu.blogsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BlogComment {
    private Integer commentId;
    private Integer blogId;
    private String commentator;
    private String commentBody;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentCreateTime;
    private String replyBody;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replyCreateTime;
    private Boolean commentStatus;
    private Boolean isDeleted;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentId=").append(commentId);
        sb.append(", blogId=").append(blogId);
        sb.append(", commentator=").append(commentator);
        sb.append(", commentBody=").append(commentBody);
        sb.append(", commentCreateTime=").append(commentCreateTime);
        sb.append(", replyBody=").append(replyBody);
        sb.append(", replyCreateTime=").append(replyCreateTime);
        sb.append(", commentStatus=").append(commentStatus);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}
