package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
