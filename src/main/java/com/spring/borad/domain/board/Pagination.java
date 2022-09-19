package com.spring.borad.domain.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pagination {
    //한페이지 목록의 개수
    private int listSize = 10;

    //페이지 표시 범위 ex)1~10, 1~5 ...
    private int rangeSize = 10;

    //현재 목록의 페이지 번호
    private int page;

    //총 게시물 개수
    private int listCnt;

    //총 페이지 개수
    private int pageCnt;

    //페이지 시작 번호
    private int rangeStart;

    //페이지 시작 끝 번호
    private int rangeEnd;

    //목록 시작 번호
    private int start;

    //목록 끝번호
    private int end;

    //이전 페이지 가능 여부
    private boolean prev;

    //다음 페이지 가능 여부
    private boolean next;

    public void pageinfo(int page, int listCnt){
        this.page=page;
        this.listCnt=listCnt;

        this.pageCnt = (int) Math.ceil(listCnt/listSize);
        this.start = (this.page*this.listSize)-listSize+1;
        this.end = this.start + listSize - 1;
        this.rangeStart = ((int) Math.ceil(page/ (double)rangeSize))*rangeSize - (rangeSize-1);
        if(rangeStart < 1){
            this.rangeStart = 1;
        }
        this.rangeEnd = rangeEnd > pageCnt ? pageCnt:rangeStart + (rangeSize -1);
        this.prev = this.rangeStart <= 1 ? false : true;
        this.next = this.rangeStart+rangeSize >= pageCnt ? false : true;
    }
}
