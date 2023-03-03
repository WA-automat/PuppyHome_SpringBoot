package com.puppyhome.backend.service.apply;

import com.puppyhome.backend.utils.ResponseResult;

public interface ApplicationService {
	ResponseResult sendApplication(String token, String name, String telephone, String description) throws Exception;

	ResponseResult showAllApply(String token) throws Exception;

	ResponseResult deleteApply(Integer userId);

	ResponseResult rejectApply(String token, Integer userId) throws Exception;

	ResponseResult acceptApply(String token, Integer userId) throws Exception;

	ResponseResult deleteAuthentication(String token, Integer userId) throws Exception;
}
