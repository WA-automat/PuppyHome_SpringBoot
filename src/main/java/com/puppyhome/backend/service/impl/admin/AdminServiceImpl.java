package com.puppyhome.backend.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.admin.AdminService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public ResponseResult showGeneralAdmin(String token) throws Exception {

		// 获取超级管理员信息
		// 获取用户权限信息
		String subject = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, subject);

		User user = userMapper.selectOne(queryWrapper);
		if (Objects.isNull(user)) {
			return ResponseResult.fail("该用户不存在");
		}

		Integer authentication = user.getAuthentication();

		if (authentication != 2) {
			return ResponseResult.fail("权限不足，无法访问");
		}

		// 获取管理员信息
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(User::getAuthentication, 1);
		List<User> generalAdmin = userMapper.selectList(lambdaQueryWrapper);

		// 好看的流
		generalAdmin.forEach(adminUser -> adminUser.setOpenId("secretOpenId!"));

		// 返回信息
		Map<String, Object> map = new HashMap<>();
		map.put("generalAdmin", generalAdmin);

		return new ResponseResult(200, "获取成功", map);
	}

	@Override
	public ResponseResult showSuperAdmin(String token) throws Exception {

		// 获取用户权限信息
		String subject = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, subject);

		User user = userMapper.selectOne(queryWrapper);
		if (Objects.isNull(user)) {
			return ResponseResult.fail("该用户不存在");
		}

		Integer authentication = user.getAuthentication();

		if (authentication != 2) {
			return ResponseResult.fail("权限不足，无法访问");
		}

		// 获取超级管理员信息
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(User::getAuthentication, 2);
		List<User> superAdmin = userMapper.selectList(lambdaQueryWrapper);

		// 同样用了foreach
		superAdmin.forEach(adminUser -> adminUser.setOpenId("secretOpenId!"));

		// 返回信息
		Map<String, Object> map = new HashMap<>();
		map.put("superAdmin", superAdmin);

		return new ResponseResult(200, "获取成功", map);
	}
}
