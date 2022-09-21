package com.spring.borad.domain.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JoinForm {

    @NotBlank(message = "아이디 입력")
    private String id;
    @NotBlank(message = "비밀번호 입력")
    private String password;
    @NotBlank(message = "이름 입력")
    private String name;
}
