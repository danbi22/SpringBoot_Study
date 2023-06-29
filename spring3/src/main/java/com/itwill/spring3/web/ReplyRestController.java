package com.itwill.spring3.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.spring3.dto.ReplyCreateDto;
import com.itwill.spring3.repository.reply.Reply;
import com.itwill.spring3.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyRestController {

	private final ReplyService replyService;
	
	@GetMapping("/all/{postId}")
	public ResponseEntity<List<Reply>> searchReplyByPostId(@PathVariable Long postId) {
		log.info("searchReplyByPostId(postId={})", postId);
		
		List<Reply> list = replyService.readByPostId(postId);
		
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<List<Reply>> create(@RequestBody ReplyCreateDto dto) {
		log.info("create(dto={})", dto);
		
		Reply reply = replyService.create(dto);
		log.info(reply.toString());
		
		List<Reply> list = replyService.readByPostId(dto.getPostId());
		
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete(@PathVariable Long id) {
		
		replyService.delete(id);
		
		return ResponseEntity.ok("1");
	}
}
