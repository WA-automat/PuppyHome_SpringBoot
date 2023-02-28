package com.puppyhome.backend.controller;

import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/hello")
	public ResponseResult Hello() {
		return ResponseResult.success("测试成功", "Hello world!");
	}

}
