package edu.xmu.blogsystem.service;

import edu.xmu.blogsystem.entity.BlogComment;

import java.util.Map;

public interface CommentService {
    Boolean addComment(BlogComment comment);
    Boolean checkDone(Integer[] ids);
    Boolean deleteBatch(Integer[] ids);
    Boolean reply(Integer commentId, String replyBody);
    Map<String, Object> getCommentPageByBlogIdAndPageNum(Integer blogId, Integer page, Integer limit);
    int getTotalComments();
}
