package com.itwill.spring3.repository.member;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.itwill.spring3.repository.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "MEMBERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@SequenceGenerator(name = "MEMBERS_SEQ_GEN",sequenceName = "MEMBERS_SEQ", allocationSize = 1)
// Member IS-A UserDetails
// 스프링 시큐리티는 로그인 처리를 위해서 UserDetails 객체를 사용하기 때문에
// 회원 정보 엔터티는 UserDetails 인터페이스를 구현해야 함.
public class Member extends BaseTimeEntity implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBERS_SEQ_GEN")
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private Role role;
	
	@Builder
	public Member(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = Role.USER; // 회원 가입 사용자 권한의 기본값은 USER
	}
	
	// UserDetails 인터페이스를 구현하는 메서드들
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// ROLE_USER 권한을 갖음.
		return Arrays.asList(new SimpleGrantedAuthority(role.getKey()));
	}

	@Override
	// 계정이 만료되지 않았느냐 (false = 만료됨, true = 만료되지 않음)
	// 계정을 1년동안 사용하지 않으면 휴면계정으로 만료시킴
	public boolean isAccountNonExpired() {
		return true; // 계정(account)이 non-expired(만료되지 않음)
	}

	@Override
	// 계정이 잠겼는냐? (false = 잠김, true = 잠기지 않음)
	// 비밀번호를 n회 틀릴 시 lock
	public boolean isAccountNonLocked() {
		return true; // 계정이 non-lock(잠기지 않음)
	}

	@Override
	// 비밀번호가 만료되지 않았느냐 (false = 만료됨, true = 만료되지 않음)
	// 계정을 1년동안 사용하지 않으면 휴면계정으로 만료시킴
	public boolean isCredentialsNonExpired() {
		return true; // 비밀번호가 non-expired
	}

	@Override
	// 사용자 상세정보(UserDetails)가 활성화되었느냐
	// 회원탈퇴를 하면 바로 회원정보를 삭제하지 않음
	// -> 회원탈퇴를 했는데 이후 다시 결제 정보 등을 가져오고 싶을 수 있으니 유예기간을 준다.
	public boolean isEnabled() {
		return true; // 사용자가 상세정보(UserDetails)가 활성화(enable).
	}
	
}
