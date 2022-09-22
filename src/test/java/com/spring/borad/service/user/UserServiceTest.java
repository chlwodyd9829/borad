package com.spring.borad.service.user;

import com.spring.borad.domain.user.JoinForm;
import com.spring.borad.domain.user.LoginForm;
import com.spring.borad.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    /**
     * HttpServletRequest의 구현 테스트 할 때 검증 하기 위해 가상의 요청 용도
     */
    private final MockHttpServletRequest request = new MockHttpServletRequest();

    @Test
    void login() throws NoSuchAlgorithmException {
        JoinForm joinForm = new JoinForm("id", "pw", "name");
        User join = userService.join(joinForm);

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("loginUser",join);
        request.setSession(mockHttpSession);

        LoginForm loginForm = new LoginForm("id","pw");
        User login = userService.login(loginForm);

        assertThat(login.getId()).isEqualTo(join.getId());
        assertThat(login.getPassword()).isEqualTo(join.getPassword());
        assertThat(login.getName()).isEqualTo(join.getName());

        //세션 검증
        HttpSession session = request.getSession(false);
        User loginUser = (User)session.getAttribute("loginUser");

        assertThat(login.getId()).isEqualTo(loginUser.getId());
        assertThat(login.getPassword()).isEqualTo(loginUser.getPassword());
        assertThat(login.getName()).isEqualTo(loginUser.getName());
    }

    @Test
    void logout() {
        User user = new User("id","pw","name");
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("loginUser",user);
        request.setSession(mockHttpSession);
        boolean logout = userService.logout(request);
        assertThat(logout).isEqualTo(true);
    }

    @Test
    void join() throws NoSuchAlgorithmException {
        JoinForm joinForm = new JoinForm("id","pw", "name");

        User join = userService.join(joinForm);

        User user = userService.findUser(join);

        assertThat(join.getId()).isEqualTo(user.getId());
        assertThat(join.getPassword()).isEqualTo(user.getPassword());
        assertThat(join.getName()).isEqualTo(user.getName());
    }
}