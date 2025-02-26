package org.example.expert.domain.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 전
        // HttpServletRequest를 사전처리 어드민 권한 여부 확인, 인증 되지 않은 사용자 접근 차단 (Filter에서 Jwt 검증)
        UserRole userRole = (UserRole) request.getAttribute("userRole");
        if (userRole == null) return false;
        return (userRole == UserRole.ADMIN) ? true : false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 실행 후
        // 어드민 접근 가능 로직 실행

        String requestURI = request.getRequestURI();

        // deleteComment() & changeUserRole()
        if (requestURI.equals("/admin/comment/delete") || requestURI.equals("/admin/user/changeRole")) {
            log.info("관리자 접근 - 요청 시각: {}, 요청 URL: {}", System.currentTimeMillis(), requestURI);

        }
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        // 요청 완료 후
//    }
}
