package com.whatpl.swagger.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatpl.swagger.test.data.ResponseData;
import com.whatpl.swagger.test.dto.MemberDTO;
import com.whatpl.swagger.test.service.SwaggerTestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/swagger/test")
public class SwaggerTestController {
	
	private final SwaggerTestService saSwaggerTestService;
	
	@PostMapping
	public ResponseEntity<ResponseData<MemberDTO>> test(@RequestBody String id) {
		ResponseData resData = new ResponseData("G000");
		return ResponseEntity.ok(saSwaggerTestService.getTest(id));
	}
}
