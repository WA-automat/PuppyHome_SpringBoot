package com.puppyhome.backend.service.impl.predict;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.puppyhome.backend.mapper.TypeMapper;
import com.puppyhome.backend.pojo.Type;
import com.puppyhome.backend.service.predict.ImagePredictService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class ImagePredictServiceImpl implements ImagePredictService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TypeMapper typeMapper;

	@Override
	@Async("asyncThreadPoolTaskExecutor")
	public Future<ResponseResult> imagePredict(String url) {

		// 通过url获取图片
		String loadUrl = "http://localhost:5000/load/model?url=" + url + "?imageMogr2/format/jpg";
		JSONObject json = restTemplate.getForEntity(loadUrl, JSONObject.class).getBody();

//		System.out.println(json);

		assert json != null;
		Object probability = json.get("probability");
		Integer typeId = Integer.parseInt((String) json.get("type")) + 1;

		LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Type::getId, typeId);
		Type type = typeMapper.selectOne(wrapper);

		Map<String, Object> map = new HashMap<>();
		map.put("className", type.getType());
		map.put("probability", probability);

		return AsyncResult.forValue(new ResponseResult(200, "获取成功", map));
	}
}
