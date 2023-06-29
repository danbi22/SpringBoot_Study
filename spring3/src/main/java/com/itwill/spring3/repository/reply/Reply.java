package com.itwill.spring3.repository.reply;

import com.itwill.spring3.repository.BaseTimeEntity;
import com.itwill.spring3.repository.post.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@ToString(exclude = {"post"})
@Entity
@Table(name="REPLIES")
@SequenceGenerator(name = "REPLIES_SEQ_GEN", sequenceName = "REPLIES_SEQ", allocationSize = 1)
public class Reply extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(generator = "REPLIES_SEQ_GEN", strategy =GenerationType.SEQUENCE                           )
	private Long id; // primary Key
	
	@ManyToOne(fetch = FetchType.LAZY) // EAGER(기본값): 즉시로딩, LAZY: 지연로딩
	private Post post; // Foreign key, 관계를 맺고 있는 엔터티.
	
	@Column(nullable = false)
	private String replyText; // 댓글 내용
	
	@Column(nullable = false)
	private String writer; // 댓글 작성자
	
	
	
	
}
