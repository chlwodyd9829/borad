package com.spring.borad.domain.user;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginForm {

    @NotBlank(message = "아이디 입력")
    private String id;
    @NotBlank(message = "비밀번호 입력")
    private String password;
}
