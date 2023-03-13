package com.puppyhome.backend.service.impl.adoption;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.*;
import com.puppyhome.backend.pojo.*;
import com.puppyhome.backend.service.adoption.AdoptMessageService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AdoptMessageServiceImpl implements AdoptMessageService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AdoptionMapper adoptionMapper;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private CollectMapper collectMapper;

	@Autowired
	private DogMapper dogMapper;

	@Override
	public ResponseResult sendAdoptMessage(String token, Integer articleId) throws Exception {

		// 获取用户Id
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(queryWrapper);
		Integer userId = user.getId();

		// 数据库中存入申请
		Adoption adoption = new Adoption(
				null, userId, articleId
		);
		adoptionMapper.insert(adoption);

		return ResponseResult.success("成功发送请求");
	}

	@Override
	public void deleteAdoptMessage(Integer articleId) {

		// 直接删除收养信息
		LambdaQueryWrapper<Adoption> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Adoption::getArticleId, articleId);
		adoptionMapper.delete(queryWrapper);

	}

	@Override
	public ResponseResult acceptAdoptMessage(String token, Integer adoptId) throws Exception {

		// 获取用户信息
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>();
		lambda.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(lambda);
		Integer userId = user.getId();

		// 获取收养请求信息
		LambdaQueryWrapper<Adoption> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Adoption::getId, adoptId);
		Adoption adoption = adoptionMapper.selectOne(queryWrapper);

		// 获取请求人信息
		LambdaQueryWrapper<User> ownerQueryWrapper = new LambdaQueryWrapper<>();
		ownerQueryWrapper.eq(User::getId, adoption.getUserId());
		User adoptUser = userMapper.selectOne(ownerQueryWrapper);

		// 获取文章信息
		Integer articleId = adoption.getArticleId();
		LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
		articleQueryWrapper.eq(Article::getId, articleId);
		Article article = articleMapper.selectOne(articleQueryWrapper);
		Integer ownerId = article.getUserId();

		// 判断权限
		if (!Objects.equals(userId, ownerId)) {
			return ResponseResult.fail("权限不足");
		}

		// 删除这篇文章的收藏
		LambdaQueryWrapper<Collect> collectLambdaQueryWrapper = new LambdaQueryWrapper<>();
		collectLambdaQueryWrapper.eq(Collect::getArticleId, articleId);
		collectMapper.delete(collectLambdaQueryWrapper);

		// 删除这篇文章的所有收养请求
		LambdaQueryWrapper<Adoption> adoptionQueryWrapper = new LambdaQueryWrapper<>();
		adoptionQueryWrapper.eq(Adoption::getArticleId, articleId);
		adoptionMapper.delete(adoptionQueryWrapper);

		// 将这篇文章的小狗标识为已收养
		LambdaQueryWrapper<Dog> dogLambdaQueryWrapper = new LambdaQueryWrapper<>();
		dogLambdaQueryWrapper.eq(Dog::getId, article.getDogId());
		Dog dog = dogMapper.selectOne(dogLambdaQueryWrapper);
		dog.setState(1);
		dogMapper.updateById(dog);

		// 返回收养者信息以供联系
		Map<String, Object> map = new HashMap<>();
		adoptUser.setOpenId("secretOpenId!");
		map.put("user", adoptUser);

		return new ResponseResult<>(200, "收养成功", map);
	}

	@Override
	public ResponseResult showAdoptMessageFromMe(String token) throws Exception {

		// 获取用户信息
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>();
		lambda.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(lambda);
		Integer userId = user.getId();

		// 获取收养信息


		return null;
	}

	@Override
	public ResponseResult showAdoptMessageToMe(String token) throws Exception {

		// 获取用户信息
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>();
		lambda.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(lambda);
		Integer userId = user.getId();

		// 获取收养信息


		return null;
	}

	@Override
	public ResponseResult ignoreAdoptMessage(String token, Integer adoptId) throws Exception {

		// 获取用户信息
		String openId = JwtUtil.parseJWT(token).getSubject();
		LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>();
		lambda.eq(User::getOpenId, openId);
		User user = userMapper.selectOne(lambda);
		Integer userId = user.getId();

		// 获取收养请求信息
		LambdaQueryWrapper<Adoption> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Adoption::getId, adoptId);
		Adoption adoption = adoptionMapper.selectOne(queryWrapper);

		// 获取文章信息
		Integer articleId = adoption.getArticleId();
		LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
		articleQueryWrapper.eq(Article::getId, articleId);
		Article article = articleMapper.selectOne(articleQueryWrapper);
		Integer ownerId = article.getUserId();

		// 判断权限
		if (!Objects.equals(userId, ownerId)) {
			return ResponseResult.fail("权限不足");
		}

		// 忽略当前申请
		// 即从数据库中删除
		adoptionMapper.deleteById(adoption);

		return ResponseResult.success("操作成功");
	}
}
