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

		if (user.getAuthentication() != 0) {
			return ResponseResult.fail("您已经是管理员");
		}

		Integer userId = user.getId();

		// 添加新的申请
		LambdaQueryWrapper<Application> lambdaQueryWrap = new LambdaQueryWrapper<>();
		lambdaQueryWrap.eq(Application::getUserId, userId);
		Application application = applicationMapper.selectOne(lambdaQueryWrap);
		if (!Objects.isNull(application)) {
			return ResponseResult.fail("请勿重复申请");
		}
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

	@Override
	public ResponseResult deleteApply(Integer userId) {
		// 查询并删除申请
		LambdaQueryWrapper<Application> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Application::getUserId, userId);

		Application application = applicationMapper.selectOne(queryWrapper);

		if (application == null) {
			return ResponseResult.fail("没有找到该申请");
		}

		applicationMapper.delete(queryWrapper);

		return ResponseResult.success("拒绝申请成功");
	}

	@Override
	public ResponseResult rejectApply(String token, Integer userId) throws Exception {

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

		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(User::getId, userId);

		User userEntity = userMapper.selectOne(lambdaQueryWrapper);
		if (Objects.isNull(userEntity)) {
			return ResponseResult.fail("删除用户不存在");
		}

		return deleteApply(userId);
	}

	@Override
	public ResponseResult acceptApply(String token, Integer userId) throws Exception {

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

		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(User::getId, userId);

		User userEntity = userMapper.selectOne(lambdaQueryWrapper);
		if (Objects.isNull(userEntity)) {
			return ResponseResult.fail("该用户不存在");
		}

		// 删除申请
		ResponseResult responseResult = deleteApply(userId);
		if (responseResult.getCode() == 400) {
			return ResponseResult.fail("没有找到该申请");
		}

		// 更新权限信息
		userEntity.setAuthentication(1);
		userMapper.updateById(userEntity);

		return ResponseResult.success("接收申请成功");
	}

	@Override
	public ResponseResult deleteAuthentication(String token, Integer userId) throws Exception {

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

		// 更新权限
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(User::getId, userId);
		User userEntity = userMapper.selectOne(lambdaQueryWrapper);
		userEntity.setAuthentication(0);
		userMapper.updateById(userEntity);

		return ResponseResult.success("删除权限成功");
	}

}
