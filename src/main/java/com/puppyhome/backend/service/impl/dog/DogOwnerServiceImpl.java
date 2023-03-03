package com.puppyhome.backend.service.impl.dog;

import com.puppyhome.backend.mapper.DogMapper;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.dog.DogOwnerService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DogOwnerServiceImpl implements DogOwnerService {

	@Autowired
	private DogMapper dogMapper;

	@Override
	public ResponseResult selectDogsOwnerById(Integer id) {
		User user = dogMapper.selectDogsOwnerById(id);
		user.setOpenId("secretOpenId!");
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		return new ResponseResult(200, "获取成功", map);
	}
}
