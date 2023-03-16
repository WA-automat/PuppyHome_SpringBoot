package com.puppyhome.backend.controller;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.puppyhome.backend.service.predict.ImagePredictService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImagePredictController {

	@Autowired
	private ImagePredictService imagePredictService;

	@GetMapping("/predict")
	public ResponseResult imagePredict(
			@RequestParam("url") String url
	) throws TranslateException, ModelNotFoundException,
			MalformedModelException, IOException {
		return imagePredictService.imagePredict(url);
	}

}
