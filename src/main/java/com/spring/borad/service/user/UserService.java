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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    /**
     * 로그인 기능
     */
    public User login(LoginForm loginForm) throws NoSuchAlgorithmException {
        String encrypt = encrypt(loginForm.getPassword());
        return userRepository.findById(loginForm.getId())
                .filter(user -> user.getPassword().equals(encrypt))
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
    public User join(JoinForm joinForm) throws NoSuchAlgorithmException {
        User findUser = userRepository.findById(joinForm.getId()).stream().findAny().orElse(null);
        if(findUser != null){
            return null;
        }
        String encrypt = encrypt(joinForm.getPassword());
        User user = new User(joinForm.getId(), encrypt, joinForm.getName());
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

    /**
     *
     * @param str
     * @return String
     * @throws NoSuchAlgorithmException
     * 비밀번호 암호화 - SHA-256 사용
     */
    private String encrypt(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(str.getBytes());

        return bytesToHex(md.digest());
    }

    /**
     *
     * @param bytes
     * @return String
     * 바이트 코드 -> 16진수
     */
    private String bytesToHex(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes){
            builder.append(String.format("%02x",b));
        }
        return builder.toString();
    }
}
