package com.puppyhome.backend.service.impl.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.ArticleMapper;
import com.puppyhome.backend.mapper.DogMapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.Article;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.article.SearchService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private DogMapper dogMapper;

	@Override
	public ResponseResult searchArticle(String token, String subType) throws Exception {

		// 获取用户信息
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>();
		lambda.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(lambda);
		Integer userId = user.getId();

		// 查询内容
		List<Article> articles = articleMapper.selectArticleBySubDogType(userId, subType);

		// 根据文章获取对应的小狗
		List<Dog> dogs = new ArrayList<>();
		for (Article article : articles) {
			Integer dogId = article.getDogId();
			LambdaQueryWrapper<Dog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(Dog::getId, dogId);
			Dog dog = dogMapper.selectOne(lambdaQueryWrapper);
			dogs.add(dog);
		}

		// 获取返回信息
		Map<String, Object> map = new HashMap<>();
		map.put("articles", articles);
		map.put("dogs", dogs);

		return new ResponseResult<>(200, "获取成功", map);
	}
}
