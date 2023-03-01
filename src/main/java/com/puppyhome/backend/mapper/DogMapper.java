package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DogMapper extends BaseMapper<Dog> {
	User selectDogsOwnerById(Integer id);
}
