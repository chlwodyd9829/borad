package com.spring.borad.repository.board;

import com.spring.borad.domain.board.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Posting, Long> {
    @Query(value = "select * from posting where name = ?1",nativeQuery = true)
    List<Posting> findByName(String name);
}
