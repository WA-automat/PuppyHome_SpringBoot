package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Adoption;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdoptionMapper extends BaseMapper<Adoption> {
}
