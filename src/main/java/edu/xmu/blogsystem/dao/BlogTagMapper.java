package edu.xmu.blogsystem.dao;

import edu.xmu.blogsystem.entity.BlogTag;
import edu.xmu.blogsystem.entity.BlogTagCount;

import java.util.List;

public interface BlogTagMapper {

    int deleteByPrimaryKey(Integer tagId);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByPrimaryKey(Integer tagId);

    BlogTag selectByTagName(String tagName);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);

    List<BlogTag> findTagList();

    List<BlogTagCount> getTagCount();

    int getTotalTags();

    int deleteBatch(Integer[] ids);

    int batchInsertBlogTag(List<BlogTag> tagList);
}
