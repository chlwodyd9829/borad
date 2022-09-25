package com.spring.borad.domain.board;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "viewCnt")
    @ColumnDefault("0")
    private int viewCnt;

    public Posting(String title, String name, String content) {
        this.title = title;
        this.name = name;
        this.content = content;
    }
}
