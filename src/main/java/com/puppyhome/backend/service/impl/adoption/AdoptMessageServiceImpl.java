package com.puppyhome.backend.service.impl.adoption;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.AdoptionMapper;
import com.puppyhome.backend.mapper.UserMapper;
import com.puppyhome.backend.pojo.Adoption;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.adoption.AdoptMessageService;
import com.puppyhome.backend.utils.JwtUtil;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdoptMessageServiceImpl implements AdoptMessageService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AdoptionMapper adoptionMapper;

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
}
