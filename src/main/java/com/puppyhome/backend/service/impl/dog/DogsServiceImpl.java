package com.puppyhome.backend.service.impl.dog;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.DogMapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.dog.DogsService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DogsServiceImpl implements DogsService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DogMapper dogMapper;

	@Override
	public ResponseResult getAllDogs() {
		Map<String, Object> map = new HashMap<>();
		map.put("dogs", dogMapper.selectList(null));
		return new ResponseResult(200, "获取成功", map);
	}

	@Override
	public ResponseResult getAdoptedDogs() {
		Map<String, Object> map = new HashMap<>();
		LambdaQueryWrapper<Dog> queryWrapper = new LambdaQueryWrapper<Dog>();
		queryWrapper.eq(Dog::getState, 1);
		map.put("dogs", dogMapper.selectList(queryWrapper));
		return new ResponseResult(200, "获取成功", map);
	}

	@Override
	public ResponseResult getUnAdoptedDogs() {
		Map<String, Object> map = new HashMap<>();
		LambdaQueryWrapper<Dog> queryWrapper = new LambdaQueryWrapper<Dog>();
		queryWrapper.eq(Dog::getState, 0);
		map.put("dogs", dogMapper.selectList(queryWrapper));
		return new ResponseResult(200, "获取成功", map);
	}

	@Override
	public ResponseResult addDog(
			String token, String dogName, String photo,
			Integer gender, Double age, String type
	) throws Exception {

		// 获取openId
		String openId = JwtUtil.parseJWT(token).getSubject();

		// 得到用户信息
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);

		// 用户不存在时
		if (Objects.isNull(user)) {
			return ResponseResult.fail("该用户不存在");
		}

		Dog dog = new Dog(
				null, dogName, photo, gender,
				age, type, user.getId(), 0
		);
		dogMapper.insert(dog);

		return new ResponseResult(200, "添加成功", null);
	}

	@SneakyThrows
	@Override
	public ResponseResult getUnAdoptedDogsExceptMine(String token) {

		// 获取用户的openId
		String openId = JwtUtil.parseJWT(token).getSubject();

		// 获取用户对象
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);

		// 判断用户是否存在
		if (Objects.isNull(user)) {
			return ResponseResult.fail("该用户不存在");
		}

		List<Dog> dogs = userMapper.selectUnAdoptedDogsExceptMineByUserId(user.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("dogs", dogs);

		return new ResponseResult<>(200, "获取成功", map);
	}

}
