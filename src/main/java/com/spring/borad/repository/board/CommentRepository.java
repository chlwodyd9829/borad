package com.spring.borad.repository.board;

import com.spring.borad.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "select * from comment where board_id=?1",nativeQuery = true)
    List<Comment> findByBoardId(Long boardId);
}
