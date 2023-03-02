package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.apply.ApplicationService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apply")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;

	@PostMapping("/send")
	public ResponseResult sendApply(
			@RequestParam("token") String token,
			@RequestParam("name") String name,
			@RequestParam("telephone") String telephone,
			@RequestParam("description") String description
	) throws Exception {
		return applicationService.sendApplication(
				token, name, telephone, description
		);
	}

	@GetMapping("show/all")
	public ResponseResult showAllApply(
			@RequestParam("token") String token
	) throws Exception {
		return applicationService.showAllApply(token);
	}

}
