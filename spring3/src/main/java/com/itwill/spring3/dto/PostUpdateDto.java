package com.itwill.spring3.dto;

import com.itwill.spring3.repository.post.Post;

import lombok.Data;

@Data
public class PostUpdateDto {
	
	private Long id;
	private String title;
	private String content;
	private String author;
	
	public Post toEntity() {
		return Post.builder()
				.id(id)
				.title(title)
				.content(content)
				.author(author)
				.build();
	}
}
