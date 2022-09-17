package com.spring.borad.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "userId")
    private String userId;

    @Column(name="title")
    private String title;

    @Column(name = "postTime")
    private String postTime;

    @Column(name = "viewCnt")
    private int viewCnt;

}
