package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.article.ArticleService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@PostMapping("/create")
	public ResponseResult createArticle(
			@RequestParam("token") String token,
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("publishTime") Date publishTime,
			@RequestParam("dogName") String dogName,
			@RequestParam("photo") String photo,
			@RequestParam("gender") Integer gender,
			@RequestParam("age") Double age,
			@RequestParam("type") String type
	) throws Exception {
		return articleService.createArticle(
				token, title, description, publishTime,
				dogName, photo, gender, age, type
		);
	}
}
