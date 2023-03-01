package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.user.UserInfoService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@GetMapping("/get/info")
	public ResponseResult getUserInfo(
			@RequestParam("token") String token
	) throws Exception {
		return userInfoService.getUserInfo(token);
	}

	@PostMapping("/set/info")
	public ResponseResult setUserInfo(
			@RequestParam("token") String token,
			@RequestParam("nickName") String nickName,
			@RequestParam("avatar") String avatar,
			@RequestParam("realName") String realName,
			@RequestParam("age") Double age,
			@RequestParam("gender") Integer gender,
			@RequestParam("telephone") String telephone
	) throws Exception {
		return userInfoService.setUserInfo(
				token, nickName, avatar, realName, age, gender, telephone
		);
	}

	@GetMapping("/dogs")
	public ResponseResult getDogsList(
			@RequestParam("token") String token
	) throws Exception {
		return userInfoService.getDogsList(token);
	}

}
