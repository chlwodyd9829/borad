package com.spring.borad.service.board;

import com.spring.borad.domain.board.BoardVO;
import com.spring.borad.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

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
}
