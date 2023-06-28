package com.itwill.spring3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.spring3.dto.PostCreateDto;
import com.itwill.spring3.dto.PostSearchDto;
import com.itwill.spring3.dto.PostUpdateDto;
import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@GetMapping
	public String read(Model model, int page_num) {
		log.info("read()");
		
		List<Post> list = postService.readAPage(page_num);
		
		List<Integer> pages = postService.countPost();
		
		
		model.addAttribute("pages", pages);
		model.addAttribute("posts", list);
		
		return "/post/read";
	}
	
	@GetMapping("/create")
	public void create() {
		log.info("create() GET");
	}
	
	@PostMapping("/create")
	public String postCreate(PostCreateDto dto) {
		log.info("postCreate(post: {})", dto);
		
		postService.insert(dto);
		
		return "redirect:/post";
	}
	
	// "/post/datails", "/post/modify" 요청 주소들을 처리하는 컨트롤러 메서드.
	@GetMapping({"/details", "/modify"}) // 요청이 들어온 주소의 이름으로 view를 찾는다.
	public void read(Long id, Model model) {
		log.info("read(id={})", id);
		
		// POSTS 테이블에서 id에 해당하는 포스트를 검색. 
		Post post = postService.read(id);
		
		// model에 post 넣어주기
		model.addAttribute("post", post);
		
		// 컨트롤러 메서드의 리턴값이 없는 경우(void인 경우)
		// 뷰의 이름은 요청 주소와 같다!
		// 
	}
	
	@PostMapping("/update")
	public String update(PostUpdateDto dto, Model model) {
		log.info("update(dto={})", dto);
		postService.update(dto);
		
		return "redirect:/post/details?id="+dto.getId();
	}
	
	@PostMapping("/delete")
	public String delete(Long id) {
		log.info("delete(id={})", id);
		
		postService.delete(id);
		
		return "redirect:/post";
	}
	
	@GetMapping("/search")
	public String search(PostSearchDto dto) {
		log.info("search(dto={})", dto);
		
		List<Post> list = postService.search(dto);
		
		return "/post/read";
	}
	
}
