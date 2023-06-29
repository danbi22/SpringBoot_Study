package com.itwill.spring3.dto;

import com.itwill.spring3.repository.reply.Reply;

import lombok.Data;

@Data
public class ReplyCreateDto {
	
	private Long postId;
	private String replyText;
	private String writer;
	
	public Reply toEntity(ReplyCreateDto dto) {
		return Reply.builder()
				.replyText(replyText)
				.writer(writer)
				.build();
				
	}
}
