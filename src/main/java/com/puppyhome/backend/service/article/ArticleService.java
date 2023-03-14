package com.puppyhome.backend.service.article;

import com.puppyhome.backend.utils.ResponseResult;

import java.util.Date;

public interface ArticleService {
	ResponseResult createArticle(
			String token, String title, String description,
			Date publishTime, String dogName, String photo,
			Integer gender, Double age, String type
	) throws Exception;

	ResponseResult getArticleMsg(String token, Integer articleId) throws Exception;

	ResponseResult deleteArticle(String token, Integer articleId) throws Exception;

	ResponseResult getUnAdoptedExceptMine(String token) throws Exception;

	ResponseResult getMyArticle(String token) throws Exception;

	ResponseResult getMyAdoptedArticle(String token) throws Exception;

	ResponseResult getMyUnadoptedArticle(String token) throws Exception;
}
