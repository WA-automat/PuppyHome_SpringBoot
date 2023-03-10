package com.puppyhome.backend.controller;

import com.puppyhome.backend.service.adoption.AdoptMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adopt")
public class AdoptMessageController {

	@Autowired
	private AdoptMessageService adoptMessageService;

}
