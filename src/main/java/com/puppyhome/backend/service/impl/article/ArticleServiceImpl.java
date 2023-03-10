package com.puppyhome.backend.service.impl.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.ArticleMapper;
import com.puppyhome.backend.mapper.CollectMapper;
import com.puppyhome.backend.mapper.DogMapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.Article;
import com.puppyhome.backend.pojo.Collect;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.article.ArticleService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DogMapper dogMapper;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private CollectMapper collectMapper;

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

	@Override
	public ResponseResult getArticleMsg(String token, Integer articleId) throws Exception {

		// 获取userId
		String subject = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(User::getOpenId, subject);
		User user = userMapper.selectOne(wrapper);
		Integer userId = user.getId();

		// 获取文章
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Article::getId, articleId);
		Article article = articleMapper.selectOne(queryWrapper);

		// 获取文章中对应的修勾
		LambdaQueryWrapper<Dog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(Dog::getId, article.getDogId());
		Dog dog = dogMapper.selectOne(lambdaQueryWrapper);

		// 返回对象
		Map<String, Object> map = new HashMap<>();
		map.put("article", article);
		map.put("dog", dog);

		// 查询是否为被收藏的文章
		LambdaQueryWrapper<Collect> qWrapper = new LambdaQueryWrapper<>();
		qWrapper
				.eq(Collect::getUserId, userId)
				.eq(Collect::getArticleId, articleId);
		Collect collect = collectMapper.selectOne(qWrapper);
		Boolean isCollect = !Objects.isNull(collect);
		map.put("isCollect", isCollect);

		return new ResponseResult(200, "获取成功", map);
	}

	@Override
	public ResponseResult deleteArticle(String token, Integer articleId) throws Exception {

		// 获取文章
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Article::getId, articleId);
		Article article = articleMapper.selectOne(queryWrapper);

		// 获取userId
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(lambdaQueryWrapper);

		// 判断权限
		if (!Objects.equals(user.getId(), article.getUserId())
				&& user.getAuthentication() == 0
		) {
			return ResponseResult.fail("权限不足，无法删除文章");
		}

		// 删除所有收藏
		LambdaQueryWrapper<Collect> lambdaQuery = new LambdaQueryWrapper<>();
		lambdaQuery.eq(Collect::getArticleId, articleId);
		collectMapper.delete(lambdaQuery);

		// 删除文章
		articleMapper.deleteById(article);

		return ResponseResult.success("删除文章成功");
	}

	@Override
	public ResponseResult getUnAdoptedExceptMine(
			String token
	) throws Exception {

		// 获取userId
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);
		Integer userId = user.getId();

		List<Article> articles = articleMapper.selectAllArticleByUserId(userId);

		List<Dog> dogs = new ArrayList<>();
		for (Article article : articles) {
			Integer dogId = article.getDogId();
			LambdaQueryWrapper<Dog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(Dog::getId, dogId);
			Dog dog = dogMapper.selectOne(lambdaQueryWrapper);
			dogs.add(dog);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("articles", articles);
		map.put("dogs", dogs);

		return new ResponseResult<>(200, "获取成功", map);
	}

	@Override
	public ResponseResult getMyArticle(String token) throws Exception {

		// 获取userId
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);
		Integer userId = user.getId();

		// 获取所有自己的文章
		LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(Article::getUserId, userId);
		List<Article> articles = articleMapper.selectList(lambdaQueryWrapper);

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

}
