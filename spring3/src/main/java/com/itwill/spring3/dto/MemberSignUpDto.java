package com.itwill.spring3.dto;

import com.itwill.spring3.repository.member.Member;

import lombok.Data;

@Data
public class MemberSignUpDto {
	
	private String username;
	private String password;
	private String email;
	
	public Member toEntity() {
		return Member.builder()
					.username(username)
					.password(password)
					.email(email)
					.build();
	}
}
