package com.itwill.spring3.repository.post;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.spring3.dto.PostUpdateDto;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@SpringBootTest
public class PostRepositoryTest {

	@Autowired // 스프링프레임워크에서 의존성 주입, 제어의 역전, 생성해줌
	private PostRepository postRepository;
	
	//@Test
	public void testFindAll() {
		List<Post> list = postRepository.findByOrderByIdDesc();
		for (Post x : list) {
			log.info(x.toString());
		}
	}
	
	@Test
	public void testSave() {
		// DB 테이블에 insert할 레코드(엔터티)를 생성:
		Post entity = Post.builder()
				.title("jpa신기해")
				.content("신기하누")
				.author("찬희")
				.build();
		log.info("insert 전: {}", entity);
		log.info("created: {}, modified: {}", entity.getCreatedTime(), entity.getModifiedTime());
		
		// DB 테이블에 insert:
			postRepository.saveAndFlush(entity);
		//-> save 메서드는 테이블에 삽입할 엔터티를 파라미터에 전달하면, 
		//-> 테이블에 저장된 엔터티 객체를 리턴.
		//-> 파라미터에 전달된 엔터티 필드를 변경해서 리턴
		// 열시퀀스 값을 다시 찾아 올 수 있음.
		
		log.info("insert 후: {}", entity); log.info("created: {}, modified: {}",
		entity.getCreatedTime(), entity.getModifiedTime()); assertNotNull(entity);
	}
	
	//@Test
	public void testUpdate() {
		// 업데이트 하기 전의 엔터티 검색:
		Post entity = postRepository.findById(1L) // 검색 결과가 있을 수도 있고 없을 수도 있기 때문에 optional타입을 사용함.
				.orElseThrow(); // orElseThrow()를 통해 optional객체가 결과가 있으면 Post객체를 리턴하고 그렇지 않으면 예외를 리턴한다
		log.info("update 전: {}", entity);
		log.info("update 전 수정시간: {}", entity.getModifiedTime());
		
		PostUpdateDto dto = new PostUpdateDto();
		dto.setTitle("JPA update 테스트");
		dto.setContent("JPA Hibernate를 사용한 DB 테이블 업데이트");
		
		// 엔터티를 수정:
		entity.update(dto);
		
		// DB 테이블 업데이트:
		// JPA에서는 insert와 update 메서드가 구분되어 있지 않음.
		// save() 메서드의 아규먼트가 DB에 없는 엔터티이면 insert, DB에 없는 엔터티이면 update를 실행.
		postRepository.saveAndFlush(entity);
		log.info(entity.toString());
	}
	
	//@Test
	public void delete() {
		long count = postRepository.count(); // DB 테이블의 행의 개수(엔터티 개수)
		log.info("삭제 전 count: {}", count);
		
		postRepository.deleteById(2L);
		
		count = postRepository.count(); // DB 테이블의 행의 개수(엔터티 개수)
		log.info("삭제 후 count: {}", count);
	}
	
	
}
