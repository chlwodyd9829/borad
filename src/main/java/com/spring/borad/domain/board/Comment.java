package com.spring.borad.domain.board;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "boardId")
    private Long boardId;
    @Column(name="userId")
    private String userId;
    @Column(name="content")
    private String content;
}
