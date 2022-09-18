package com.spring.borad.repository.board;

import com.spring.borad.domain.board.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardVO,Long> {
    @Query(value = "select * from boardvo order by id limit ?1 offset ?2", nativeQuery = true)
    List<BoardVO> findStartEnd(int listSize, int start);
}
