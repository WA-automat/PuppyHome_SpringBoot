package com.puppyhome.backend.service.impl.user;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.user.LoginService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private Long port;

	private final String AppId = "wx5451a7d6f84032d6";    // 公众平台自己的appId

	private final String AppSecret = "09ba46b7831f83dd921e8aaafd6258c9";    // AppSecret

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserMapper userMapper;

	@Override
	public ResponseResult wxLogin(String code) {

		String url = "https://api.weixin.qq.com/sns/jscode2session?" +
				"appid=" + AppId +
				"&secret=" + AppSecret +
				"&js_code=" + code +
				"&grant_type=authorization_code";

		//利用spring原生http请求工具对接口进行请求
		String jsonData = restTemplate.getForObject(url, String.class);
		JSONObject jsonObject = JSONObject.parseObject(jsonData);

		assert jsonData != null;
		if (StringUtils.contains(jsonData, "errcode")) {
			return ResponseResult.fail("登录失败");
		}

		// 获取内容
		assert jsonObject != null;
		String openid = jsonObject.getString("openid");
		String sessionKey = jsonObject.getString("session_key");
		String unionid = jsonObject.getString("unionid");

		// 生成jwt
		String jwt = JwtUtil.createJWT(openid);

		// 连接redis
		Jedis jedis = new Jedis(host, Math.toIntExact((port)));
		jedis.connect();

		// 用户信息存入redis
		jedis.set("wxLogin:" + jwt, JSON.toJSONString(jsonObject));
		// 登录有效期最长为3天(即3天后必须重新登录)
		jedis.expire("wxLogin:" + jwt, 3 * 24 * 60 * 60L);

		// 创建返回的data
		Map<String, String> map = new HashMap<>();
		map.put("token", jwt);

		// 查看这个用户的信息
		// 导入匿名MyBatis-Plus查询类
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openid);
		User user = userMapper.selectOne(queryWrapper);

		if (user != null) {
			map.put("hasObj", "true");
		} else {
			map.put("hasObj", "false");
		}

		return new ResponseResult(200, "登录成功", map);
	}
}
