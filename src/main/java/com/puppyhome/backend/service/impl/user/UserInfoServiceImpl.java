package com.puppyhome.backend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.user.UserInfoService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserMapper userMapper;

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

		// 当这个用户存在时返回对应的用户信息
		Map<String, Object> map = new HashMap<>();
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
			avatar = null;
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
					null, openId, nickName, avatar, realName, age, gender, telephone
			);
			userMapper.insert(userEntity);

			return ResponseResult.success("保存信息成功");

		}
	}
}
