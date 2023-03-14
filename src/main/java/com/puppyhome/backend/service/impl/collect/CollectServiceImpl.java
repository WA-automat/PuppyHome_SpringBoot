package com.puppyhome.backend.service.impl.collect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.CollectMapper;
import com.puppyhome.backend.mapper.DogMapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.Article;
import com.puppyhome.backend.pojo.Collect;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.collect.CollectService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImpl implements CollectService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CollectMapper collectMapper;

	@Autowired
	private DogMapper dogMapper;

	@Override
	public ResponseResult getMyCollect(String token) throws Exception {

		// 获取用户Id
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);
		Integer userId = user.getId();

		// 数据库创建方法获取文章信息
		List<Article> articles = collectMapper.selectArticleByUserId(userId);

		List<Dog> dogs = new ArrayList<>();
		for (Article article : articles) {
			Integer dogId = article.getDogId();
			LambdaQueryWrapper<Dog> dogLambdaQueryWrapper = new LambdaQueryWrapper<>();
			dogLambdaQueryWrapper.eq(Dog::getId, dogId);
			Dog dog = dogMapper.selectOne(dogLambdaQueryWrapper);
			dogs.add(dog);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("articles", articles);
		map.put("dogs", dogs);

		return new ResponseResult<>(200, "获取成功", map);
	}

	@Override
	public ResponseResult addCollect(String token, Integer articleId) throws Exception {

		// 获取用户Id
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);
		Integer userId = user.getId();

		// 判断是否重复收藏
		LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper
				.eq(Collect::getUserId, userId)
				.eq(Collect::getArticleId, articleId);
		Collect oldCollect = collectMapper.selectOne(lambdaQueryWrapper);
		if (oldCollect != null) {
			return ResponseResult.fail("请勿重复收藏");
		}

		// 添加一个收藏o
		Collect collect = new Collect(null, userId, articleId);
		collectMapper.insert(collect);

		return ResponseResult.success("添加收藏成功");
	}

	@Override
	public ResponseResult deleteCollect(String token, Integer articleId) throws Exception {

		// 获取用户Id
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);
		Integer userId = user.getId();

		// 删除收藏
		LambdaQueryWrapper<Collect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper
				.eq(Collect::getUserId, userId)
				.eq(Collect::getArticleId, articleId);
		collectMapper.delete(lambdaQueryWrapper);

		return ResponseResult.success("删除收藏成功");
	}
}
