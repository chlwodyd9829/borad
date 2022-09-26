package com.spring.borad.repository.board;

import com.spring.borad.domain.board.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardVO,Long> {
    @Query(value = "select * from boardvo order by id limit ?1 offset ?2", nativeQuery = true)
    List<BoardVO> findStartEnd(int listSize, int start);

    @Transactional
    @Modifying
    @Query(value = "update boardvo set view_Cnt = ?1 where id = ?2", nativeQuery = true)
    void increaseViewCnt(int cnt, Long id);

    @Transactional
    @Modifying
    @Query(value = "update boardvo set title = ?1 , post_Time = ?2 where id = ?3",nativeQuery = true)
    void updateBoard(String title, String postTime, Long id);
}
