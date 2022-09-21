package com.spring.borad.controller;

import com.spring.borad.domain.board.BoardVO;
import com.spring.borad.domain.user.JoinForm;
import com.spring.borad.domain.user.LoginForm;
import com.spring.borad.domain.user.User;
import com.spring.borad.service.board.Pagination;
import com.spring.borad.service.board.BoardService;
import com.spring.borad.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    //메인 화면, 게시글 목록이 보인다.
    @PostConstruct
    public void init(){
        for (int i = 0; i < 200; i++){
            Long id = Long.valueOf(i+1);
            BoardVO boardVO = new BoardVO();
            boardVO.setId(id);
            boardVO.setUserId(id.toString());
            boardVO.setTitle(id.toString()) ;
            boardVO.setViewCnt(0);
            boardService.savePost(boardVO);
        }
        JoinForm joinForm = new JoinForm("asd", "123", "name");
        userService.join(joinForm);
    }
    @GetMapping
    public String home(@RequestParam(required = false, defaultValue = "1")int page, Model model,HttpServletRequest request) {
        Pagination pagination = new Pagination();
        int listCnt = boardService.getListCnt();
        pagination.pageinfo(page,listCnt);
        log.info("pagination={}", pagination.toString());
        model.addAttribute("pagination", pagination);
        List<BoardVO> boardVOList = boardService.boardVOPageList(pagination.getListSize(),pagination.getStart()-1);
        model.addAttribute("boardList",boardVOList);

        if(request.getSession() != null){
            User loginUser = (User) request.getSession().getAttribute("loginUser");
            model.addAttribute("loginUser",loginUser);
        }
        return "board_Home";
    }
    @GetMapping("/write")
    public String post(){
        return "write";
    }

    @GetMapping("/join")
    public String join(@ModelAttribute JoinForm joinForm){
        return "join";
    }
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute JoinForm joinForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "join";
        }
        User join = userService.join(joinForm);
        if(join == null){
            bindingResult.reject("Exist","이미 존재하는 아이디 입니다.");
            return "join";
        }

        return "redirect:/board";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm){
        return "login";
    }
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
        if(bindingResult.hasErrors()){
            return "login";
        }
        User loginUser = userService.login(loginForm);
        if(loginUser == null){
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>");
            writer.println("alert('아이디 혹은 비밀번호를 확인 해주세요.')");
            writer.println("history.go(-1)");
            writer.println("</script>");
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginUser",loginUser);
        return "redirect:/board";
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        log.info("logout");
        if(!userService.logout(request)){
            return "board_Home";
        }
        return "redirect:/board";
    }
}
