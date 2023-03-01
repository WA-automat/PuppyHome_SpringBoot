package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Dog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DogMapper extends BaseMapper<Dog> {
}
