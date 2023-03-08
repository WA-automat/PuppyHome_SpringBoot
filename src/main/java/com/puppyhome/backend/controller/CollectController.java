package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.collect.CollectService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collect")
public class CollectController {

	@Autowired
	private CollectService collectService;

	@GetMapping("/mine")
	public ResponseResult getMyCollect(
			@RequestParam("token") String token
	) throws Exception {
		return collectService.getMyCollect(token);
	}

	@PostMapping("/add")
	public ResponseResult addCollect(
			@RequestParam("token") String token,
			@RequestParam("articleId") Integer articleId
	) throws Exception {
		return collectService.addCollect(token, articleId);
	}

	@PostMapping("/delete")
	public ResponseResult deleteCollect(
			@RequestParam("token") String token,
			@RequestParam("articleId") Integer articleId
	) throws Exception {
		return collectService.deleteCollect(token, articleId);
	}

}
