package edu.xmu.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.xmu.blogsystem.controller.vo.BlogDetailVO;
import edu.xmu.blogsystem.controller.vo.BlogListVO;
import edu.xmu.blogsystem.controller.vo.SimpleBlogListVO;
import edu.xmu.blogsystem.dao.*;
import edu.xmu.blogsystem.entity.Blog;
import edu.xmu.blogsystem.entity.BlogCategory;
import edu.xmu.blogsystem.entity.BlogTag;
import edu.xmu.blogsystem.entity.BlogTagRelation;
import edu.xmu.blogsystem.service.BlogService;
import edu.xmu.blogsystem.util.MarkDownUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private BlogCategoryMapper categoryMapper;
    @Resource
    private BlogTagMapper blogTagMapper;
    @Resource
    private BlogTagRelationMapper blogTagRelationMapper;
    @Resource
    private BlogCommentMapper blogCommentMapper;

    @Override
    public Map<String, Object> getAllBlogs() {
        List<Blog> list = blogMapper.getAllBlogs();
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", list.size());
        return result;
    }

    @Override
    public Map<String, Object> getBlogsPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Blog> list = blogMapper.getAllBlogs();
        PageInfo<Blog> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", pageInfo.getTotal());
        return result;
    }

    @Override
    @Transactional
    public String saveBlog(Blog blog) {
        BlogCategory blogCategory = categoryMapper.selectById(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量最大为6";
        }
        //保存文章
        if (blogMapper.insert(blog) > 0) {
            //新增的tag对象
            List<BlogTag> tagListForInsert = new ArrayList<>();
            //所有的tag对象，用于建立关系数据
            List<BlogTag> allTagsList = new ArrayList<>();
            for (String s : tags) {
                BlogTag tag = blogTagMapper.selectByTagName(s);
                if (tag == null) {
                    //tag不存在就新增
                    BlogTag tempTag = new BlogTag();
                    tempTag.setTagName(s);
                    tagListForInsert.add(tempTag);
                } else {
                    allTagsList.add(tag);
                }
            }
            //新增标签数据并修改分类排序值
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                blogTagMapper.batchInsertBlogTag(tagListForInsert);
            }
            if (blogCategory != null) {
                categoryMapper.updateById(blogCategory);
            }
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            //新增关系数据
            allTagsList.addAll(tagListForInsert);
            for (BlogTag tag : allTagsList) {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(tag.getTagId());
                blogTagRelations.add(blogTagRelation);
            }
            if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0) {
                return "success";
            }
        }
        return "保存失败";
    }
    @Override
    @Transactional
    public String updateBlog(Blog blog) {
        Blog blogForUpdate = blogMapper.selectById(blog.getBlogId());
        if (blogForUpdate == null) {
            return "数据不存在";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setEnableComment(blog.getEnableComment());
        BlogCategory blogCategory = categoryMapper.selectById(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());
        //新增的tag对象
        List<BlogTag> tagListForInsert = new ArrayList<>();
        //所有的tag对象，用于建立关系数据
        List<BlogTag> allTagsList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            BlogTag tag = blogTagMapper.selectByTagName(tags[i]);
            if (tag == null) {
                //不存在就新增
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(tags[i]);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.add(tag);
            }
        }
        //新增标签数据不为空->新增标签数据
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            blogTagMapper.batchInsertBlogTag(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        //新增关系数据
        allTagsList.addAll(tagListForInsert);
        for (BlogTag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        //修改blog信息->修改分类排序值->删除原关系数据->保存新的关系数据
        if (blogCategory != null) {
            categoryMapper.updateById(blogCategory);
        }
        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
        blogTagRelationMapper.batchInsert(blogTagRelations);
        if (blogMapper.updateById(blogForUpdate) > 0) {
            return "success";
        }
        return "修改失败";
    }

    @Override
    public Map<String, Object> getBlogsForIndexPage(int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("blogStatus", 1); //过滤发布状态下的数据
        // 每页8条
        PageHelper.startPage(page, 8);
        List<Blog> blogList = blogMapper.findBlogList(params);
        List<BlogListVO> blogListVOs = getBlogListVOsByBlogs(blogList);
        PageInfo<BlogListVO> pageInfo = new PageInfo<>(blogListVOs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", blogListVOs);
        result.put("totalCount", pageInfo.getTotal());
        result.put("totalPage", pageInfo.getPages());
        result.put("currPage", page);
        result.put("pageSize", 8);
        return result;
    }

    /**
     * 首页侧边栏数据列表
     * 0-点击最多 1-最新发布
     */
    @Override
    public List<SimpleBlogListVO> getBlogListForIndexPage(int type) {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        List<Blog> blogs = blogMapper.findBlogListByType(type, 9); //最多展示9条
        if (!CollectionUtils.isEmpty(blogs)) {
            for (Blog blog : blogs) {
                SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
                BeanUtils.copyProperties(blog, simpleBlogListVO);
                simpleBlogListVOS.add(simpleBlogListVO);
            }
        }
        return simpleBlogListVOS;
    }

    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
        List<BlogListVO> blogListVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)) {
            for (Blog blog : blogList) {
                BlogListVO blogListVO = new BlogListVO();
                BeanUtils.copyProperties(blog, blogListVO);
                blogListVOS.add(blogListVO);
            }
        }
        return blogListVOS;
    }

    /**
     * 获取博客详情
     */
    @Override
    public BlogDetailVO getBlogDetail(Integer id) {
        Blog blog = blogMapper.selectById(id);
        return getBlogDetailVO(blog);
    }

    public BlogDetailVO getBlogDetailVO(Blog blog) {
        if (blog != null && blog.getBlogStatus() == 1) {
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews() + 1);
            blogMapper.updateById(blog);
            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog, blogDetailVO);
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            BlogCategory blogCategory = categoryMapper.selectById(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
            }
            //分类信息
            if (StringUtils.hasText(blog.getBlogTags())) {
                //标签设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            //设置评论数
            Map<String, Object> params = new HashMap<>();
            params.put("blogId", blog.getBlogId());
            params.put("commentStatus", 1);//过滤审核通过的数据
            blogDetailVO.setCommentCount(blogCommentMapper.getTotalBlogComments(params));
            return blogDetailVO;
        }
        return null;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogMapper.deleteBatch(ids) > 0;
    }

    @Override
    public Integer getTotalBlogs() {
        return blogMapper.getTotalBlogs(null);
    }

    @Override
    public Blog getBlogById(Integer blogId) {
        return blogMapper.selectById(blogId);
    }


}
