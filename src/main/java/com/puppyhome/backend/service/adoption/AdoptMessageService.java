package com.puppyhome.backend.service.adoption;

import com.puppyhome.backend.utils.ResponseResult;

public interface AdoptMessageService {
	ResponseResult sendAdoptMessage(String token, Integer articleId) throws Exception;

	void deleteAdoptMessage(Integer articleId);
}
