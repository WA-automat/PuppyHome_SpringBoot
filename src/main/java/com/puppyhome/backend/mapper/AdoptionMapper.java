package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Adoption;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdoptionMapper extends BaseMapper<Adoption> {
	List<Adoption> selectAdoptionByArticleOwnerId(Integer articleOwnerId);
}
