package com.spring.borad.service;

import com.spring.borad.domain.board.BoardVO;
import com.spring.borad.repository.board.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void save(){
        BoardVO boardVO = new BoardVO(1L,"asd", "aaa", "123123", 5);
        Long boardId = boardVO.getId();

        boardRepository.save(boardVO);


        BoardVO findBoard = boardRepository.findById(boardId).stream().findAny().orElse(null);

        Assertions.assertThat(boardVO.getId()).isEqualTo(findBoard.getId());
        Assertions.assertThat(boardVO.getUserId()).isEqualTo(findBoard.getUserId());
        Assertions.assertThat(boardVO.getTitle()).isEqualTo(findBoard.getTitle());
        Assertions.assertThat(boardVO.getPostTime()).isEqualTo(findBoard.getPostTime());
        Assertions.assertThat(boardVO.getViewCnt()).isEqualTo(findBoard.getViewCnt());
    }
}