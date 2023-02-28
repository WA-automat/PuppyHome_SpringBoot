package com.puppyhome.backend.service.user;

import com.puppyhome.backend.utils.ResponseResult;

/**
 * 微信小程序登录服务接口类
 */
public interface LoginService {
	ResponseResult wxLogin(String code);
}
