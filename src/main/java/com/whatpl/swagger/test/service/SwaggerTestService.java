package com.whatpl.swagger.test.service;

import org.springframework.stereotype.Service;

import com.whatpl.swagger.test.data.ResponseData;
import com.whatpl.swagger.test.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SwaggerTestService {
	public ResponseData<MemberDTO> getTest(final String id) {
		MemberDTO mem = new MemberDTO();
		mem.setIdx("1");
		mem.setEmail("g@gmail.com");
		mem.setPassword("test123456");
		ResponseData<MemberDTO> res = new ResponseData<MemberDTO>("G000");
		res.setData(mem);
		return res;
	}
	
	public String creaetTest() {
		return null;
	}
}
