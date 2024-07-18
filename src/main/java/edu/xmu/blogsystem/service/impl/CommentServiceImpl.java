package edu.xmu.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xmu.blogsystem.dao.BlogCommentMapper;
import edu.xmu.blogsystem.entity.BlogComment;
import edu.xmu.blogsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private BlogCommentMapper blogCommentMapper;
    @Override
    public Boolean addComment(BlogComment comment) {
        return blogCommentMapper.addComment(comment);
    }
    @Override
    public int getTotalComments() {
        return blogCommentMapper.getTotalBlogComments(null);
    }
    @Override
    public Boolean checkDone(Integer[] ids) {
        return blogCommentMapper.checkDone(ids) > 0;
    }
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogCommentMapper.deleteBatch(ids) > 0;
    }
    @Override
    public Boolean reply(Integer commentId, String replyBody) {
        BlogComment blogComment = blogCommentMapper.selectByPrimaryKey(commentId);
        //blogComment不为空且状态为已审核，则继续后续操作
        if (blogComment != null && blogComment.getCommentStatus()) {
            blogComment.setReplyBody(replyBody);
            blogComment.setReplyCreateTime(new Date());
            return blogCommentMapper.updateByPrimaryKeySelective(blogComment) > 0;
        }
        return false;
    }
    @Override
    public Map<String, Object> getCommentPageByBlogIdAndPageNum(Integer blogId, Integer page, Integer limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("blogId", blogId);
        params.put("commentStatus", 1);//过滤审核通过的数据
        //获取所有评论
        PageHelper.startPage(page, limit);
        List<BlogComment>list = blogCommentMapper.findBlogCommentList(params);
        PageInfo<BlogComment> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", pageInfo.getTotal());
        result.put("totalPage", pageInfo.getPages());
        result.put("currPage", page);
        result.put("pageSize", limit);
        return result;
    }
}
