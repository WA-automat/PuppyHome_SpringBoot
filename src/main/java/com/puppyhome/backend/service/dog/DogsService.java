package com.puppyhome.backend.service.dog;

import com.puppyhome.backend.utils.ResponseResult;

public interface DogsService {
	ResponseResult getAllDogs();

	ResponseResult getAdoptedDogs();

	ResponseResult getUnAdoptedDogs();

	ResponseResult addDog(
			String token, String dogName, String photo,
			Integer gender, Double age, String type
	) throws Exception;

	ResponseResult getUnAdoptedDogsExceptMine(String token);
}
