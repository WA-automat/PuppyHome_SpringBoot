package com.puppyhome.backend.service.predict;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.puppyhome.backend.utils.ResponseResult;

import java.io.IOException;

public interface ImagePredictService {
	ResponseResult imagePredict(String url) throws IOException, ModelNotFoundException, MalformedModelException, TranslateException;
}
