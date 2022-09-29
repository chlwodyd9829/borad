package com.spring.borad.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {
    private static final String[] whitelist={"/board/comment/*","/board","/board/login","/board/join","/board/logout","/css/*","/js/*","/board/view/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("필터 시작 {}",requestURI);

            if(isLoginCheckPath(requestURI)){
                HttpSession session = httpRequest.getSession();
                if(session ==null || session.getAttribute("loginUser") == null){
                    log.info("미인증 {}",requestURI);
                    httpResponse.sendRedirect("/board");
                    return;
                }
            }

            chain.doFilter(request,response);
        }catch (Exception e){
            throw e;
        }
        finally{
            log.info("필터 종료 {}", requestURI);
        }
    }

    /**
     * URI가 검증 해야하는 Path인지 체크
     * @param requestURI
     * @return
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist,requestURI);
    }
}
