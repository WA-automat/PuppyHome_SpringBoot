package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.adoption.AdoptMessageService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adopt")
public class AdoptMessageController {

	@Autowired
	private AdoptMessageService adoptMessageService;

	@PostMapping("send")
	private ResponseResult sendAdoptMessage(
			@RequestParam("token") String token,
			@RequestParam("articleId") Integer articleId
	) throws Exception {
		return adoptMessageService.sendAdoptMessage(token, articleId);
	}

}
