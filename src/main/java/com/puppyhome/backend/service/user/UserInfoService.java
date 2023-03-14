package com.puppyhome.backend.service.user;

import com.puppyhome.backend.utils.ResponseResult;

import java.util.concurrent.Future;

/**
 * 获取用户信息的接口类
 */
public interface UserInfoService {
	Future<ResponseResult> getUserInfo(String token) throws Exception;

	ResponseResult setUserInfo(
			String token, String nickName, String avatar, String realName,
			Double age, Integer gender, String telephone
	) throws Exception;

	ResponseResult getDogsList(String token) throws Exception;
}
