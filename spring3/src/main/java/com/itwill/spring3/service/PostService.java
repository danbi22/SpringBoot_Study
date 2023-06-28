package com.itwill.spring3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.spring3.dto.PostCreateDto;
import com.itwill.spring3.dto.PostSearchDto;
import com.itwill.spring3.dto.PostUpdateDto;
import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor // final로 선언된 것들을 생성해줌
public class PostService {
	
	// 생성자를 사용한 의존성 주입
	private final PostRepository postRepository;
	
	// DB POSTS 테이블에서 전체 검색한 결과를 리턴.
	@Transactional(readOnly = true)
	public List<Post> read() {
		return postRepository.findByOrderByIdDesc();
	}
	
	// DB POSTS 테이블에 엔터티를 삽입(insert):
	public Post insert(PostCreateDto dto) {
		log.info("create(dto={})", dto);
		
		// DTO를 Entity로 변환:
		Post entity = dto.toEntity();
		log.info("entity={}", entity);
		
		// entity를 DB에 저장
		postRepository.save(entity);
		log.info("entity={}", entity);
		
		return entity;
	}
	
	@Transactional(readOnly = true)
	public Post read(Long id) {
		log.info("read(id={}", id);
		return postRepository.findById(id).orElseThrow();
	}

	public void delete(Long id) {
		log.info("delete(id={}", id);
		postRepository.deleteById(id);
		
	}
	
	// (1) 메서드에 @Transactional 애너테이션을 설정하고
	// (2) DB에서 엔터티를 검색하고,
	// (3) 검색한 엔터티를 수정하면,
	// 트랜잭션이 끝나는 시점에 DB update가 자동으로 수행됨
	@Transactional // (1)
	public void update(PostUpdateDto dto) {
		log.info("update(id={}", dto);
		
		Post entity = postRepository.findById(dto.getId()).orElseThrow(); // (2)
		
	}
	
	
	
//	public void update(PostUpdateDto dto) {
//		log.info("update(id={}", dto);
//		
//		Post post = dto.toEntity();
//		
//		postRepository.save(post);
//	}

	public List<Post> readAPage(int page_num) {
		log.info("readAPage(page_num={})", page_num);
		
		List<Post> list = postRepository.findByOrderByIdDesc();
		log.info("list.size({})", list.size());
		
		if (page_num <= 0) {
			page_num = 1;
		} else if ((page_num *10) - list.size() >= 10) {
			page_num--;
		}
		
		int page_num2 = (page_num - 1) * 10;
		
		List<Post> page = new ArrayList<>();
		
		int pages = 10;
		
		if ((page_num2 - list.size()) < 10) {
			pages = page_num2 - list.size();
		}
		
		for (int i = page_num2; i < page_num2 + pages; i++) {
			page.add(list.get(i));
		}
		
		return page;
	}

	public List<Integer> countPost() {
		long count = postRepository.count();
		
		int numberOfPage = 0; 
		
		numberOfPage += count/10;
		
		if (count%10 != 0) {
			numberOfPage++;
		}
		List <Integer> pages = new ArrayList<>();
		
		for (int i = 1; i <= numberOfPage; i++) {
			pages.add(i);
		}
		
		return pages;
	}

	public List<Post> search(PostSearchDto dto) {
		
//		postRepository.find
		return null; 
	}
}
