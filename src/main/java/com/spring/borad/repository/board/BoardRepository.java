package com.spring.borad.repository.board;

import com.spring.borad.domain.board.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardVO,Long> {
}
