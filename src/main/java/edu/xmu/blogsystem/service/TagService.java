package edu.xmu.blogsystem.service;

import edu.xmu.blogsystem.entity.BlogTagCount;

import java.util.List;
import java.util.Map;

public interface TagService {
    Map<String, Object> getBlogTagPage(Integer page, Integer limit);
    boolean saveTag(String tagName);
    boolean deleteBatch(Integer[] ids);
    int getTotalTags();
    List<BlogTagCount> getBlogTagCountForIndex();
}
