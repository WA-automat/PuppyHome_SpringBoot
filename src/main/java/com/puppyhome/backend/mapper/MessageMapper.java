package com.puppyhome.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.Message;
import com.puppyhome.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MessageMapper extends BaseMapper<Message> {
	List<User> selectFromUserByToID(Integer toId);
	List<Dog> selectDogByToId(Integer toId);
	List<Integer> selectStateByToId(Integer toId);
	List<User> selectToUserByFromID(Integer fromId);
	List<Dog> selectDogByFromId(Integer fromId);
	List<Integer> selectStateByFromId(Integer fromId);
}
