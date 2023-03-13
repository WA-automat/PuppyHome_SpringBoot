package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.adoption.AdoptMessageService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adopt")
public class AdoptMessageController {

	@Autowired
	private AdoptMessageService adoptMessageService;

	@PostMapping("/send")
	private ResponseResult sendAdoptMessage(
			@RequestParam("token") String token,
			@RequestParam("articleId") Integer articleId
	) throws Exception {
		return adoptMessageService.sendAdoptMessage(token, articleId);
	}

	@PostMapping("/accept")
	private ResponseResult acceptAdoptMessage(
			@RequestParam("token") String token,
			@RequestParam("adoptId") Integer adoptId
	) throws Exception {
		return adoptMessageService.acceptAdoptMessage(token, adoptId);
	}

	@GetMapping("/show/from")
	private ResponseResult showAdoptMessageFromMe(
			@RequestParam("token") String token
	) throws Exception {
		return adoptMessageService.showAdoptMessageFromMe(token);
	}

	@GetMapping("/show/to")
	private ResponseResult showAdoptMessageToMe(
			@RequestParam("token") String token
	) throws Exception {
		return adoptMessageService.showAdoptMessageToMe(token);
	}

	@PostMapping("/ignore")
	private ResponseResult ignoreAdoptMessage(
			@RequestParam("token") String token,
			@RequestParam("adoptId") Integer adoptId
	) throws Exception {
		return adoptMessageService.ignoreAdoptMessage(token, adoptId);
	}
}
