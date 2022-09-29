package com.spring.borad.service;

import com.spring.borad.domain.board.*;
import com.spring.borad.repository.board.BoardRepository;
import com.spring.borad.repository.board.CommentRepository;
import com.spring.borad.repository.board.PostRepository;
import com.spring.borad.service.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Autowired
    private PostRepository postRepository;


    @Test
    void save(){
        BoardVO boardVO = new BoardVO(1L,"asd", "aaa", "123123", 5);
        Long boardId = boardVO.getId();

        boardRepository.save(boardVO);


        BoardVO findBoard = boardRepository.findById(boardId).stream().findAny().orElse(null);

        assertThat(boardVO.getId()).isEqualTo(findBoard.getId());
        assertThat(boardVO.getUserId()).isEqualTo(findBoard.getUserId());
        assertThat(boardVO.getTitle()).isEqualTo(findBoard.getTitle());
        assertThat(boardVO.getPostTime()).isEqualTo(findBoard.getPostTime());
        assertThat(boardVO.getViewCnt()).isEqualTo(findBoard.getViewCnt());
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
    @Test
    void makePost(){
        PostingForm postingForm = new PostingForm("title", "name", "content");
        boardService.makePost(postingForm);

        Posting findPost = postRepository.findByName(postingForm.getName()).stream().filter(p -> p.getContent().equals(postingForm.getContent())).findAny().orElse(null);
        BoardVO findBoard = boardRepository.findById(findPost.getId()).stream().findFirst().orElse(null);


        System.out.println(findPost.toString());
        System.out.println(findBoard.toString());
        assertThat(findPost.getId()).isEqualTo(findBoard.getId());
        assertThat(findPost.getTitle()).isEqualTo(findBoard.getTitle());
        assertThat(findPost.getName()).isEqualTo(findBoard.getUserId());
    }
    @Test
    void cntTest(){
        BoardVO boardVO = new BoardVO(1L, "id", "title", "date", 0);
        BoardVO savePost = boardService.savePost(boardVO);
        boardService.viewCnt(savePost.getId());
        BoardVO findBoard = boardRepository.findById(boardVO.getId()).stream().findAny().orElse(null);
        assertThat(findBoard.getViewCnt()).isEqualTo(1);
    }

    @Test
    void updatePost(){
        PostingForm postingForm = new PostingForm("title", "name", "content");
        BoardVO boardVO = boardService.makePost(postingForm);
        boardService.update(new ViewForm(boardVO.getId(), boardVO.getTitle(), boardVO.getUserId(), "updateContent",0));

        Posting posting = boardService.viewPost(boardVO.getId());
        assertThat(posting.getContent()).isEqualTo("updateContent");
    }

    @Test
    void deletePost(){
        PostingForm postingForm = new PostingForm("title", "name", "content");
        BoardVO boardVO = boardService.makePost(postingForm);
        Long id = boardVO.getId();

        Posting posting = boardService.viewPost(id);
        id = posting.getId();
        assertThat(posting).isNotNull();

        boardService.delete(id);
        Posting findPosting = boardService.viewPost(id);
        assertThat(findPosting).isNull();
    }

    @Test
    void makeComment(){
        CommentForm commentForm = new CommentForm(1L, "userId", "content");

        Comment saveComment = boardService.makeComment(commentForm);

        assertThat(saveComment.getId()).isEqualTo(1L);
        assertThat(saveComment.getUserId()).isEqualTo("userId");
        assertThat(saveComment.getBoardId()).isEqualTo(1L);
        assertThat(saveComment.getContent()).isEqualTo("content");
    }
}