package com.itwill.spring3.repository.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.spring3.repository.post.Post;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	// Post (id)로 검색하기:
	List<Reply> findByPostOrderByIdDesc(Post post);
	
//	 Post에 달린 댓글 개수
//	@Query(
//		"select count(*) "
//		+ "from replies r "
//		+ "where r.post_id = :keyword"
//	)
//	Long countByPost(@Param("keyword") Long keyword);
	
	List<Reply> findByPostId(Long postId);
	
//	@Query(
//		"insert into replies (postId, replyText, writer, createdTime, modifiedTime) "
//		+ "values ("
//	)
	
}
