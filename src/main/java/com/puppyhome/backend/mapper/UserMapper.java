package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
	List<Dog> selectDogsByUserId(Integer id);
	List<Dog> selectUnAdoptedDogsExceptMineByUserId(Integer id);
}
