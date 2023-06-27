package com.itwill.spring3.service;

import org.springframework.stereotype.Service;

import com.itwill.spring3.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor // final로 선언된 것들을 생성해줌
public class PostService {
	
	private final PostRepository postRepository;
}
