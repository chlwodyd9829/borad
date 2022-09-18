package com.spring.borad.service;

import com.spring.borad.BoradApplication;
import com.spring.borad.domain.board.BoardVO;
import com.spring.borad.repository.board.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    @Test
    void findStartEnd(){
        for (int i = 0; i < 200; i++){
            Long id = Long.valueOf(i+1);
            BoardVO boardVO = new BoardVO();
            boardVO.setId(id);
            boardVO.setUserId(id.toString());
            boardVO.setTitle(id.toString());
            boardVO.setViewCnt(0);
            boardRepository.save(boardVO);
        }
        List<BoardVO> startEnd = boardRepository.findStartEnd(10, 1);
        System.out.println("cnt : "+startEnd.size());
        for (BoardVO boardVO : startEnd) {
            System.out.println("boardVO = " + boardVO);
        }
    }
}