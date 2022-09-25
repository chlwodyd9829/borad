package com.spring.borad.domain.board;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
    @ColumnDefault("0")
    private int viewCnt;

    public BoardVO(String userId, String title, String postTime) {
        this.userId = userId;
        this.title = title;
        this.postTime = postTime;
    }
}
