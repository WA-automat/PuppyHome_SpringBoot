package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.admin.AdminService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/general")
	public ResponseResult showGeneralAdmin(
			@RequestParam("token") String token
	) throws Exception {
		return adminService.showGeneralAdmin(token);
	}

	@GetMapping("/super")
	public ResponseResult showSuperAdmin(
			@RequestParam("token") String token
	) throws Exception {
		return adminService.showSuperAdmin(token);
	}

}
