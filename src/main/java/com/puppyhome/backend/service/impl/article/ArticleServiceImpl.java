package com.puppyhome.backend.service.impl.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.ArticleMapper;
import com.puppyhome.backend.mapper.DogMapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.Article;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.article.ArticleService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DogMapper dogMapper;

	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public ResponseResult createArticle(
			String token, String title, String description,
			Date publishTime, String dogName, String photo,
			Integer gender, Double age, String type
	) throws Exception {

		// 获取userId
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);
		Integer userId = user.getId();

		// 创建新的狗勾，并存入数据库
		Dog dog = new Dog(
				null, dogName, photo, gender,
				age, type, userId, 0
		);
		dogMapper.insert(dog);

		// 获取狗勾Id
		LambdaQueryWrapper<Dog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper
				.eq(Dog::getDogName, dogName)
				.eq(Dog::getPhoto, photo)
				.eq(Dog::getGender, gender)
				.eq(Dog::getAge, age)
				.eq(Dog::getType, type)
				.eq(Dog::getOwnerId, userId)
				.eq(Dog::getState, 0);
		Dog dogHasId = dogMapper.selectOne(lambdaQueryWrapper);

		// 创建新文章，并存入数据库
		Article article = new Article(
				null, userId, dogHasId.getId(),
				title, publishTime, description
		);
		articleMapper.insert(article);

		return ResponseResult.success("创建文章成功");
	}
}
