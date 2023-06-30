package com.itwill.spring3.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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
import com.itwill.spring3.repository.reply.Reply;
import com.itwill.spring3.repository.reply.ReplyRepository;
import com.itwill.spring3.service.PostService;
import com.itwill.spring3.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	private final ReplyService replyService;
	
	@GetMapping
	public String read(Model model, int page_num) {
		log.info("read()");
		
		long page = postService.count();
		
		if (page_num <= 0) {
			page_num = 1;
		} else if ((page_num *10) - page >= 10) {
			page_num--;
		}
		
		List<Post> list = postService.readAPage(page_num);
		
		List<Integer> pages = postService.countPost();
		
		
		model.addAttribute("pages", pages);
		model.addAttribute("posts", list);
		model.addAttribute("page_num", page_num);
		
		return "/post/read";
	}
	
	// Spring-EL = ''
	@PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그임) 여부를 확인
	@GetMapping("/create")
	public void create() {
		log.info("create() GET");
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public String postCreate(PostCreateDto dto) {
		log.info("postCreate(post: {})", dto);
		
		postService.insert(dto);
		
		return "redirect:/post";
	}
	
	// "/post/datails", "/post/modify" 요청 주소들을 처리하는 컨트롤러 메서드.
	@PreAuthorize("hasRole('USER')")
	@GetMapping({"/details", "/modify"}) // 요청이 들어온 주소의 이름으로 view를 찾는다.
	public void read(Long id, Model model) {
		log.info("read(id={})", id);
		
		// POSTS 테이블에서 id에 해당하는 포스트를 검색. 
		Post post = postService.read(id);
		
		// post에 해당하는 댓글을 검색
		List<Reply> list = replyService.read(post);
		
		// 댓글 개수를 저장
		int repliesCount = list.size();
		
		// model에 post, 댓글 목록, 댓글 개수 넣어주기
		model.addAttribute("post", post);
		model.addAttribute("replies", list);
		model.addAttribute("repliesCount", repliesCount);
		
		// 컨트롤러 메서드의 리턴값이 없는 경우(void인 경우)
		// 뷰의 이름은 요청 주소와 같다!
		// detail
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/update")
	public String update(PostUpdateDto dto, Model model) {
		log.info("update(dto={})", dto);
		postService.update(dto);
		
		return "redirect:/post/details?id="+dto.getId();
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/delete")
	public String delete(Long id) {
		log.info("delete(id={})", id);
		
		postService.delete(id);
		
		return "redirect:/post";
	}
	
	@GetMapping("/search")
	public String search(PostSearchDto dto, Model model) {
		log.info("search(dto={})", dto);
		
		List<Post> list = new ArrayList<>();
		
		switch(dto.getType()) {
			case "t":
				list = postService.searchByTitle(dto.getKeyword());
				break;
			case "c":
				list = postService.searchByContent(dto.getKeyword());
				break;
			case "a":
				list = postService.searchByAuthor(dto.getKeyword());
				break;
			case "tc":
				list = postService.searchByTitleAndContent2(dto.getKeyword());
		}
		
		model.addAttribute("posts", list);
		
		return "/post/read";
	}
	
}
