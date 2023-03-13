package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
	List<Article> selectAllArticleByUserId(Integer userId);
	List<Article> selectArticleBySubDogType(Integer userId, String subDogType);
}
