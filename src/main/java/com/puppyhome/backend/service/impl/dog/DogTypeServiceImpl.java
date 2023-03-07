package com.puppyhome.backend.service.impl.dog;

import com.puppyhome.backend.mapper.TypeMapper;
import com.puppyhome.backend.pojo.Type;
import com.puppyhome.backend.service.dog.DogTypeService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DogTypeServiceImpl implements DogTypeService {

	@Autowired
	private TypeMapper typeMapper;

	// TODO 创建数据库并将类别返回给前端
	@Override
	public ResponseResult getAllType() {

		List<String> typeList = typeMapper.selectTypes();
		Map<String, Object> map = new HashMap<>();
		map.put("typeList", typeList);
		return new ResponseResult<>(200, "获取成功", map);

	}

}
