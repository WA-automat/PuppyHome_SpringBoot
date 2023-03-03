package com.puppyhome.backend.service.admin;

import com.puppyhome.backend.utils.ResponseResult;

public interface AdminService {
	ResponseResult showGeneralAdmin(String token) throws Exception;

	ResponseResult showSuperAdmin(String token) throws Exception;
}
