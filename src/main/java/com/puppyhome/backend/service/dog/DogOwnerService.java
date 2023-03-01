package com.puppyhome.backend.service.dog;

import com.puppyhome.backend.utils.ResponseResult;

public interface DogOwnerService {
	ResponseResult selectDogsOwnerById(Integer id);
}
