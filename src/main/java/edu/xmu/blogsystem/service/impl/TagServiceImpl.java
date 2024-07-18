package edu.xmu.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xmu.blogsystem.dao.BlogTagMapper;
import edu.xmu.blogsystem.dao.BlogTagRelationMapper;
import edu.xmu.blogsystem.entity.BlogCategory;
import edu.xmu.blogsystem.entity.BlogTag;
import edu.xmu.blogsystem.entity.BlogTagCount;
import edu.xmu.blogsystem.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private BlogTagRelationMapper relationMapper;
    @Override
    public Map<String, Object> getBlogTagPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<BlogTag> list = blogTagMapper.findTagList();
        PageInfo<BlogTag> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", pageInfo.getTotal());
        return result;
    }
    @Override
    public int getTotalTags() {
        return blogTagMapper.getTotalTags();
    }

    @Override
    public boolean saveTag(String tagName) {
        BlogTag temp = blogTagMapper.selectByTagName(tagName);
        if (temp == null) {
            BlogTag blogTag = new BlogTag();
            blogTag.setTagName(tagName);
            return blogTagMapper.insertSelective(blogTag) > 0;
        }
        return false;
    }
    @Override
    public boolean deleteBatch(Integer[] ids) {
        //已存在关联关系不删除
        List<Integer> relations = relationMapper.selectDistinctTagIds(ids);
        if (!CollectionUtils.isEmpty(relations)) {
            return false;
        }
        //删除tag
        return blogTagMapper.deleteBatch(ids) > 0;
    }
    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        return blogTagMapper.getTagCount();
    }
}
