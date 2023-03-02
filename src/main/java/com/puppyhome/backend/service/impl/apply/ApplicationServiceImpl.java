package com.puppyhome.backend.service.impl.apply;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.ApplicationMapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.Application;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.apply.ApplicationService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationMapper applicationMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public ResponseResult sendApplication(String token, String name, String telephone, String description) throws Exception {

		// 获取用户id
		String subject = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, subject);

		User user = userMapper.selectOne(queryWrapper);
		if (Objects.isNull(user)) {
			return ResponseResult.fail("该用户不存在");
		}

		Integer userId = user.getId();

		// 添加新的申请
		applicationMapper.insert(new Application(
				null, userId, name, telephone, description
		));

		return ResponseResult.success("申请成功");
	}

	@Override
	public ResponseResult showAllApply(String token) throws Exception {

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

		List<Application> applications = applicationMapper.selectList(null);
		Map<String, Object> map = new HashMap<>();
		map.put("applications", applications);
		return new ResponseResult(200, "获取成功", map);

	}
}
