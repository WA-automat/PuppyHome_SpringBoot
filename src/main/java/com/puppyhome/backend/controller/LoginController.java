package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.user.LoginService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseResult wxLogin(@RequestParam("code") String code) {
		return loginService.wxLogin(code);
	}

}
