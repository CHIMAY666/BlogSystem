package edu.xmu.blogsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class BlogTag {
    private Integer tagId;
    private String tagName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Data createTime;
    private Boolean isDeleted;
}
