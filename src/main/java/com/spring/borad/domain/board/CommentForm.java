package com.spring.borad.domain.board;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {
    @NotNull
    private Long boardId;

    private String userId;

    @NotBlank(message = "내용 입력")
    private String content;
}
