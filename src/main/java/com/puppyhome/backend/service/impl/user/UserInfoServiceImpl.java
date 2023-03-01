package com.puppyhome.backend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.user.UserInfoService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserMapper userMapper;

	private final String host = "https://puppyhome-1317060763.cos.ap-guangzhou.myqcloud.com";

	@Override
	public ResponseResult getUserInfo(String token) throws Exception {

		// 获取openId
		String openId = JwtUtil.parseJWT(token).getSubject();

		// 在数据库中查询对应用户
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);

		// 当这个用户不存在时，返回提示信息
		if (Objects.isNull(user)) {
			return ResponseResult.fail("用户不存在");
		}

		// 当这个用户存在时返回对应的用户信息(隐藏openId)
		Map<String, Object> map = new HashMap<>();
		user.setId(0);
		user.setOpenId("secretOpenId!");
		map.put("user", user);

		return new ResponseResult(200, "获取用户信息成功", map);
	}

	@Override
	public ResponseResult setUserInfo(
			String token, String nickName, String avatar, String realName,
			Double age, Integer gender, String telephone
	) throws Exception {
		// 获取openId
		String openId = JwtUtil.parseJWT(token).getSubject();

		// 在数据库中查询对应用户
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);

		if (Objects.equals(avatar, "")) {
			avatar = host + "/userinfo/defaultAvatar.png";
		}

		// 当用户信息已经保存过
		if (!Objects.isNull(user)) {

			// 更新数据库信息
			user.setNickName(nickName);
			user.setAvatar(avatar);
			user.setRealName(realName);
			user.setAge(age);
			user.setGender(gender);
			user.setTelephone(telephone);
			userMapper.updateById(user);

			return ResponseResult.success("修改信息成功");

		} else {

			// 用户未注册时，添加一个新用户
			User userEntity = new User(
					null, openId, nickName, avatar, realName,
					age, gender, telephone, 0
			);
			userMapper.insert(userEntity);

			return ResponseResult.success("保存信息成功");

		}
	}

	@Override
	public ResponseResult getDogsList(String token) throws Exception {

		// 获取openId
		String openId = JwtUtil.parseJWT(token).getSubject();

		// 查询该用户
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);

		// 获取该用户的所有小狗
		if (Objects.isNull(user)) {
			return ResponseResult.fail("该用户不存在");
		}
		List<Dog> dogList = userMapper.selectDogsByUserId(user.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("dogs", dogList);

		return new ResponseResult(200, "获取成功", map);

	}
}
