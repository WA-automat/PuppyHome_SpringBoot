package com.puppyhome.backend.service.adoption;

import com.puppyhome.backend.utils.ResponseResult;

public interface AdoptMessageService {
	ResponseResult sendAdoptMessage(String token, Integer articleId) throws Exception;

	void deleteAdoptMessage(Integer articleId);

	ResponseResult acceptAdoptMessage(String token, Integer adoptId) throws Exception;

	ResponseResult showAdoptMessageFromMe(String token) throws Exception;

	ResponseResult showAdoptMessageToMe(String token) throws Exception;

	ResponseResult ignoreAdoptMessage(String token, Integer adoptId) throws Exception;
}
