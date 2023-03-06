package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.article.ArticleService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/msg")
	public ResponseResult getArticleMsg(
			@RequestParam("articleId") Integer articleId
	) {
		return articleService.getArticleMsg(articleId);
	}

	@PostMapping("/delete")
	public ResponseResult deleteArticle(
			@RequestParam("token") String token,
			@RequestParam("articleId") Integer articleId
	) throws Exception {
		return articleService.deleteArticle(token, articleId);
	}

	@GetMapping("/except")
	public ResponseResult getUnAdoptedExceptMine(
			@RequestParam("token") String token
	) throws Exception {
		return articleService.getUnAdoptedExceptMine(token);
	}

}
