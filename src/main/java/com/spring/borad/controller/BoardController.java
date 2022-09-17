package com.spring.borad.controller;

import com.spring.borad.domain.board.BoardVO;
import com.spring.borad.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //메인 화면, 게시글 목록이 보인다.
    @GetMapping
    public String home(Model model) {
        for (int i = 0; i < 10; i++){
            Long id = Long.valueOf(i+1);
            BoardVO boardVO = new BoardVO();
            boardVO.setId(id);
            boardVO.setUserId(id.toString());
            boardVO.setTitle(id.toString());
            boardVO.setViewCnt(0);
            boardService.savePost(boardVO);
        }
        List<BoardVO> boardVOList = boardService.boardVOList();
        model.addAttribute("boardList",boardVOList);
        return "board_Home";
    }
}
