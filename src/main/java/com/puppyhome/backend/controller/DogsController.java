package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.dog.DogTypeService;
import com.puppyhome.backend.service.dog.DogsService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dogs")
public class DogsController {

	@Autowired
	private DogsService dogsService;

	@Autowired
	private DogTypeService dogTypeService;

	@GetMapping("/all")
	public ResponseResult getAllDogs() {
		return dogsService.getAllDogs();
	}

	@GetMapping("/adopted")
	public ResponseResult getAdoptedDogs() {
		return dogsService.getAdoptedDogs();
	}

	@GetMapping("/unadopted")
	public ResponseResult getUnAdoptedDogs() {
		return dogsService.getUnAdoptedDogs();
	}

	@GetMapping("/except")
	public ResponseResult getUnAdoptedDogsExceptMine(
			@RequestParam("token") String token
	) throws Exception {
		return dogsService.getUnAdoptedDogsExceptMine(token);
	}

	@PostMapping("/add")
	public ResponseResult addDog(
			@RequestParam("token") String token,
			@RequestParam("dogName") String dogName,
			@RequestParam("photo") String photo,
			@RequestParam("gender") Integer gender,
			@RequestParam("age") Double age,
			@RequestParam("type") String type

	) throws Exception {
		return dogsService.addDog(
				token, dogName, photo, gender, age, type
		);
	}

	@GetMapping("/msg")
	public ResponseResult getMsg(
			@RequestParam("id") Integer id
	) {
		return dogsService.getMsg(id);
	}

	@GetMapping("/type")
	public ResponseResult getAllType() {
		return dogTypeService.getAllType();
	}

}
