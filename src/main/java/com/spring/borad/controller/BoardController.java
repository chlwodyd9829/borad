package com.spring.borad.controller;

import com.spring.borad.domain.board.*;
import com.spring.borad.domain.user.JoinForm;
import com.spring.borad.domain.user.LoginForm;
import com.spring.borad.domain.user.User;
import com.spring.borad.service.board.BoardService;
import com.spring.borad.service.board.Pagination;
import com.spring.borad.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.security.NoSuchAlgorithmException;
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
        for (int i = 0; i < 8; i++){
            PostingForm content = new PostingForm(Integer.toString(i + 1), Integer.toString(i + 1), "content");
            boardService.makePost(content);
        }
        JoinForm joinForm = new JoinForm("asd", "123", "name");
        try{
        userService.join(joinForm);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    /**
     * 게시판 메인
     */
    @GetMapping
    public String home(@RequestParam(required = false) String title ,@RequestParam(required = false, defaultValue = "1")int page, Model model,HttpServletRequest request) {
        Pagination pagination = new Pagination();
        int listCnt = boardService.getListCnt();
        pagination.pageinfo(page,listCnt);
        log.info("pagination={}", pagination.toString());
        model.addAttribute("pagination", pagination);
        List<BoardVO> boardVOList;
        boardVOList = boardService.boardVOPageList(pagination.getListSize(), pagination.getStart() - 1);
        model.addAttribute("boardList",boardVOList);

        if(request.getSession() != null){
            User loginUser = (User) request.getSession().getAttribute("loginUser");
            model.addAttribute("loginUser",loginUser);
        }
        return "board_Home";
    }

    /**
     * 글쓰기 요청
     */
    @GetMapping("/write")
    public String post(@ModelAttribute PostingForm postingForm, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        model.addAttribute("loginUser",session.getAttribute("loginUser"));
        User loginUser = (User) session.getAttribute("loginUser");
        postingForm.setName(loginUser.getId());
        return "write";
    }
    @PostMapping("/write")
    public String post(@Validated @ModelAttribute PostingForm postingForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "write";
        }
        boardService.makePost(postingForm);
        return "redirect:/board";
    }
    /**
     * 회원가입 요청
     */
    @GetMapping("/join")
    public String join(@ModelAttribute JoinForm joinForm){
        return "join";
    }
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute JoinForm joinForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "join";
        }
        try{
            User join = userService.join(joinForm);
            if(join == null){
                bindingResult.reject("Exist","이미 존재하는 아이디 입니다.");
                return "join";
            }
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "redirect:/board";
    }

    /**
     * 로그인 요청
     */
    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm){
        return "login";
    }
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
        if(bindingResult.hasErrors()){
            return "login";
        }
        try {
            User loginUser = userService.login(loginForm);
            if (loginUser == null) {
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
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "redirect:/board";
    }

    /**
     * 로그아웃 요청
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        log.info("logout");
        if(!userService.logout(request)){
            return "board_Home";
        }
        return "redirect:/board";
    }
    /**
     * 글보기
     */
    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable Long id,Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Posting posting = boardService.viewPost(id);
        boardService.viewCnt(id);
        ViewForm viewForm = new ViewForm(posting.getId(), posting.getTitle(), posting.getName(), posting.getContent(), posting.getViewCnt());
        model.addAttribute("viewForm",viewForm);
        model.addAttribute("loginUser",session.getAttribute("loginUser"));

        model.addAttribute("comment", new CommentForm());
        List<Comment> commentList = boardService.getCommentList(id);
        model.addAttribute("commentList",commentList);
        return "view";
    }
    /**
     * 글수정
     */
    @GetMapping("/update/{id}")
    public String updatePost(@PathVariable Long id, Model model){
        Posting posting = boardService.viewPost(id);
        ViewForm viewForm = new ViewForm(posting.getId(), posting.getTitle(),posting.getName(),posting.getContent(), posting.getViewCnt());
        model.addAttribute("updateForm",viewForm);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updatePost( @PathVariable Long id,@Validated @ModelAttribute ViewForm viewForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "update";
        }
        boardService.update(viewForm);
        return "redirect:/board/view/{id}";
    }
    /**
     * 글 삭제
     */
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board";
    }
    /**
     * 댓글 등록
     */
    @PostMapping("/comment/{boardId}")
    public String comment(@PathVariable Long boardId, @Validated CommentForm commentForm,BindingResult bindingResult,HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return "redirect:/board/view/{boardId}";
        }
        HttpSession session = request.getSession();
        if(session.getAttribute("loginUser") != null){
            User loginUser = (User) session.getAttribute("loginUser");
            commentForm.setUserId(loginUser.getId());
        }
        Comment comment = boardService.makeComment(commentForm);
        return "redirect:/board/view/{boardId}";
    }
}
