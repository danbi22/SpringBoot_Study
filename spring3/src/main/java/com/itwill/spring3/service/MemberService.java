package com.itwill.spring3.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwill.spring3.dto.MemberSignUpDto;
import com.itwill.spring3.repository.member.Member;
import com.itwill.spring3.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor // final로 생성된 객체를 초기화. 의존성 주입
// Security Filter Chain에서 UserDetailsService 객체를 사용할 수 있도록 하기 위해서.
public class MemberService implements UserDetailsService{

	private final MemberRepository memberRepositoty;
	
	// SecurityConfig에서 설정한 PasswordEncoder 빈(bean)을 주입해줌.
	private final PasswordEncoder passwordEncoder;
	
	// 회원 가입
	public Long registerMember(MemberSignUpDto dto) {
		log.info("registerMember(dto={})", dto);
		
		Member entity = Member.builder()
							.username(dto.getUsername())
							.password(passwordEncoder.encode(dto.getPassword()))
							.email(dto.getEmail())
							.build();
		
		log.info("save 전: entity={}", entity);
		
		memberRepositoty.save(entity);
		log.info("save 후: entity={}", entity);
		
		
		return entity.getId();
	}

	@Override
	// user id와 password를 검증하는 메서드
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername(username={})", username);
		
		// DB에서 username으로 사용자 정보 검색(select)
		UserDetails user = memberRepositoty.findByUsername(username);

		if (user != null) {
			return user;
		} 
		
		throw new UsernameNotFoundException(username + " - not found");
	}
}
