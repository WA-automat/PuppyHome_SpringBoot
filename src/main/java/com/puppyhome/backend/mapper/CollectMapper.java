package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Article;
import com.puppyhome.backend.pojo.Collect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectMapper extends BaseMapper<Collect> {
	List<Article> selectArticleByUserId(Integer userId);
}
