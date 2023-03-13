package com.puppyhome.backend.service.article;

import com.puppyhome.backend.utils.ResponseResult;

public interface SearchService {
	ResponseResult searchArticle(String token, String subType) throws Exception;
}
