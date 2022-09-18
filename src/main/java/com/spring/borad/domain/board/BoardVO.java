package com.spring.borad.domain.board;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
