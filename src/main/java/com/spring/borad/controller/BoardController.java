package com.spring.borad.controller;

import com.spring.borad.domain.board.BoardVO;
import com.spring.borad.domain.board.Pagination;
import com.spring.borad.service.BoardService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    //메인 화면, 게시글 목록이 보인다.
    @GetMapping
    public String home(@RequestParam(required = false, defaultValue = "1")int page, Model model) {
        Pagination pagination = new Pagination();
        for (int i = 0; i < 200; i++){
            Long id = Long.valueOf(i+1);
            BoardVO boardVO = new BoardVO();
            boardVO.setId(id);
            boardVO.setUserId(id.toString());
            boardVO.setTitle(id.toString()) ;
            boardVO.setViewCnt(0);
            boardService.savePost(boardVO);
        }
        int listCnt = boardService.getListCnt();
        pagination.pageinfo(page,listCnt);
        log.info("pagination={}", pagination.toString());
        model.addAttribute("pagination", pagination);
        List<BoardVO> boardVOList = boardService.boardVOPageList(pagination.getListSize(),pagination.getStart()-1);
        model.addAttribute("boardList",boardVOList);
        return "board_Home";
    }
}
