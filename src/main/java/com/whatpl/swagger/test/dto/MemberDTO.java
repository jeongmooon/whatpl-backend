package com.whatpl.swagger.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
	
	private String idx;
	@NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "이메일은 두 글자 이상")
    @Email
	private String email;
	@NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "비밀번호는 여덟 글자 이상")
	private String password;
}
