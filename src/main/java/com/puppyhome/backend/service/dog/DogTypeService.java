package com.puppyhome.backend.service.dog;

import com.puppyhome.backend.utils.ResponseResult;

import java.util.concurrent.Future;

public interface DogTypeService {
	Future<ResponseResult> getAllType();
}
