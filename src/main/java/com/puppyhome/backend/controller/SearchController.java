package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.article.SearchService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@GetMapping("/article")
	public ResponseResult searchArticle(
			@RequestParam("token") String token,
			@RequestParam("subType") String subType
	) throws Exception {
		return searchService.searchArticle(token, subType);
	}

}
