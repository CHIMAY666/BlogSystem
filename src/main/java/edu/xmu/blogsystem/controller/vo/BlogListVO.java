package edu.xmu.blogsystem.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogListVO implements Serializable {
    private Integer blogId;
    private String blogTitle;
    private String blogCoverImage;
    private Integer blogCategoryId;
    private String blogCategoryName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
}
