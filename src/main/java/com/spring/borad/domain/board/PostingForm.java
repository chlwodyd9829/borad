package com.spring.borad.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostingForm {
    @NotBlank(message = "제목 입력")
    private String title;
    @NotBlank(message = "이름")
    private String name;
    @NotBlank(message = "내용 입력")
    private String content;
}
