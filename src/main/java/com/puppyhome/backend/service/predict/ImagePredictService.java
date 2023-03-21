package com.puppyhome.backend.service.predict;

import com.puppyhome.backend.utils.ResponseResult;

import java.io.IOException;
import java.util.concurrent.Future;

public interface ImagePredictService {
	Future<ResponseResult> imagePredict(String url) throws IOException;
}
