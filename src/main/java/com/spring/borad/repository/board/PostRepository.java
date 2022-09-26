package com.spring.borad.repository.board;

import com.spring.borad.domain.board.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepository extends JpaRepository<Posting, Long> {
    @Query(value = "select * from posting where name = ?1",nativeQuery = true)
    List<Posting> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update Posting set title = ?1 , content=?2 where id = ?3",nativeQuery = true)
    void updatePosting(String title, String content, Long id);
}
