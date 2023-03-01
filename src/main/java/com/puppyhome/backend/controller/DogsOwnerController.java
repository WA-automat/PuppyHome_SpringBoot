package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.dog.DogOwnerService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dogs")
public class DogsOwnerController {

	@Autowired
	private DogOwnerService dogOwnerService;

	@GetMapping("/owner")
	public ResponseResult selectDogsOwnerById(
			@RequestParam("id") Integer id
	){
		return dogOwnerService.selectDogsOwnerById(id);
	}

}
