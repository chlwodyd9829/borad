package com.spring.borad.service.user;

import com.spring.borad.domain.user.JoinForm;
import com.spring.borad.domain.user.LoginForm;
import com.spring.borad.domain.user.User;
import com.spring.borad.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    /**
     * 로그인 기능
     */
    public User login(LoginForm loginForm){
        return userRepository.findById(loginForm.getId())
                .filter(user -> user.getPassword().equals(loginForm.getPassword()))
                .orElse(null);
    }
    /**
     * 로그아웃
     */
    public boolean logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
            return true;
        }
        return false;
    }
    /**
     * 회원가입
     */
    public User join(JoinForm joinForm){
        User findUser = userRepository.findById(joinForm.getId()).stream().findAny().orElse(null);
        if(findUser != null){
            return null;
        }
        User user = new User(joinForm.getId(), joinForm.getPassword(), joinForm.getName());
        User save = userRepository.save(user);
        log.info("save User={}",save);
        return save;
    }
    /**
     * 회원 조회
     */
    public User findUser(User user){
        Optional<User> find = userRepository.findById(user.getId());
        return find.stream().findAny().orElse(null);
    }
}
