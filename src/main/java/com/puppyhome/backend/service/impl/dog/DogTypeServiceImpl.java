package com.puppyhome.backend.service.impl.dog;

import com.puppyhome.backend.mapper.TypeMapper;
import com.puppyhome.backend.service.dog.DogTypeService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class DogTypeServiceImpl implements DogTypeService {

	@Autowired
	private TypeMapper typeMapper;

	@Override
	@Async("asyncThreadPoolTaskExecutor")
	public Future<ResponseResult> getAllType() {

		List<String> typeList = typeMapper.selectTypes();
		Map<String, Object> map = new HashMap<>();
		map.put("typeList", typeList);
		return new AsyncResult<>(new ResponseResult<>(200, "获取成功", map));

	}

}
