package com.puppyhome.backend.service.collect;

import com.puppyhome.backend.utils.ResponseResult;

public interface CollectService {
	ResponseResult getMyCollect(String token) throws Exception;
	
	ResponseResult addCollect(String token, Integer articleId) throws Exception;

	ResponseResult deleteCollect(String token, Integer articleId) throws Exception;
}
