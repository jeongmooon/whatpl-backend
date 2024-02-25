package com.whatpl.swagger.test.data;

import com.whatpl.swagger.test.error.HttpCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
public class ResponseData<T> {

	private String status;
	private String messgae;
	private T data;
	private String accessToken;
	
	public ResponseData() {
	}
	
	public ResponseData(String code) {
		for (HttpCode httpCode : HttpCode.values()) {
            if(httpCode.getCode().equals(code)) {
            	this.status = httpCode.getStatus();
            	this.messgae = httpCode.getMessage();
            }
		}
	}
}
