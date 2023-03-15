package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.predict.ImagePredictService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImagePredictController {

	@Autowired
	private ImagePredictService imagePredictService;

	@GetMapping("/predict")
	public ResponseResult imagePredict(
			@RequestParam("url") String url
	){
		return imagePredictService.imagePredict(url);
	}

}
