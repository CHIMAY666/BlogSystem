package edu.xmu.blogsystem.dao;

import edu.xmu.blogsystem.entity.BlogTagRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogTagRelationMapper {
    int deleteByPrimaryKey(Integer relationId);

    int insert(BlogTagRelation record);

    int insertSelective(BlogTagRelation record);

    BlogTagRelation selectByPrimaryKey(Integer relationId);

    BlogTagRelation selectByBlogIdAndTagId(Integer blogId, Integer tagId);

    List<Integer> selectDistinctTagIds(Integer[] tagIds);

    int updateByPrimaryKeySelective(BlogTagRelation record);

    int updateByPrimaryKey(BlogTagRelation record);

    int batchInsert(List<BlogTagRelation> blogTagRelationList);

    int deleteByBlogId(Integer blogId);
}
