package com.spring.borad.service.board;

import com.spring.borad.domain.board.BoardVO;
import com.spring.borad.domain.board.Posting;
import com.spring.borad.domain.board.PostingForm;
import com.spring.borad.domain.board.ViewForm;
import com.spring.borad.repository.board.BoardRepository;
import com.spring.borad.repository.board.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    /**
     * 모든 게시글 목록 반환
     */
    public List<BoardVO> boardVOList(){
        List<BoardVO> boardVOList = boardRepository.findAll();
        return boardVOList;
    }

    /**
     * 게시글을 저장하는 기능(현재는 목록만 저장)
     */
    public BoardVO savePost(BoardVO boardVO){
        boardVO.setPostTime(makeDate());
        BoardVO save = boardRepository.save(boardVO);
        return save;
    }
    /**
     * 작성날짜 만들어주는 기능
     */
    private String makeDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String format = simpleDateFormat.format(calendar.getTime());
        return format;
    }
    /**
     * 페이지 개수 반환
     */
    public int getListCnt(){
        long count = boardRepository.count();
        return Long.valueOf(count).intValue();
    }
    /**
     * 페이지 범위에 해당 하는 목록 반환
     */
    public List<BoardVO> boardVOPageList(int rangeSize, int start){
        return boardRepository.findStartEnd(rangeSize,start);
    }
    /**
     * 게시글 등록
     */
    public void makePost(PostingForm postingForm){
        Posting posting = new Posting(postingForm.getTitle(), postingForm.getName(), postingForm.getContent());
        postRepository.save(posting);
        savePost(new BoardVO(posting.getName(), posting.getTitle(), null));
    }
    /**
     * 게시글 보기
     */
    public Posting viewPost(Long id){
        Posting posting = postRepository.findById(id).stream().findAny().orElse(null);
        return posting;
    }
    /**
     * 게시글 조회수
     */
    public void viewCnt(Long id){
        BoardVO boardVO = boardRepository.findById(id).stream().findAny().orElse(null);
        boardVO.setViewCnt(boardVO.getViewCnt()+1);
        log.info(boardVO.toString());
        boardRepository.increaseViewCnt(boardVO.getViewCnt(), boardVO.getId());
    }
    /**
     * 게시글 수정
     */
    public void update(ViewForm viewForm){
        boardRepository.updateBoard(viewForm.getTitle(), makeDate(), viewForm.getId());
        postRepository.updatePosting(viewForm.getTitle(), viewForm.getContent(), viewForm.getId());
    }
}
