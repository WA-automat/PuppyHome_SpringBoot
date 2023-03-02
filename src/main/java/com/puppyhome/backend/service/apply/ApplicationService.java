package com.puppyhome.backend.service.apply;

import com.puppyhome.backend.utils.ResponseResult;

public interface ApplicationService {
	ResponseResult sendApplication(String token, String name, String telephone, String description) throws Exception;

	ResponseResult showAllApply(String token) throws Exception;
}
