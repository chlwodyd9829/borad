package com.spring.borad.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostingForm {
    @NotBlank(message = "제목 입력")
    private String title;
    @NotBlank(message = "이름")
    private String name;
    @NotBlank(message = "내용 입력")
    @Size(max=255,message = "글자 수가 많습니다.")
    private String content;
}
