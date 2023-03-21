package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.predict.ImagePredictService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/image")
public class ImagePredictController {

	@Autowired
	private ImagePredictService imagePredictService;

	@GetMapping("/predict")
	public ResponseResult imagePredict(
			@RequestParam("url") String url
	) throws IOException, ExecutionException, InterruptedException, TimeoutException {
		return imagePredictService.imagePredict(url).get(180, TimeUnit.SECONDS);
	}

}
