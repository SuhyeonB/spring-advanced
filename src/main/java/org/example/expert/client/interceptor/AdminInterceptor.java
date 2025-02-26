package org.example.expert.client.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;


@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandler");
        // 요청 전
        // HttpServletRequest를 사전처리 어드민 권한 여부 확인, 인증 되지 않은 사용자 접근 차단 (Filter에서 Jwt 검증)
        UserRole userRole = (request.getAttribute("userRole").equals("ADMIN")) ? UserRole.ADMIN : UserRole.USER;
        log.info("userRole: {}",userRole);
        if (userRole == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "삭제 불가");
            return false;
        }
        if (userRole.equals(UserRole.USER)) {
            log.info("접근 차단 시간: {}", LocalDateTime.now());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만이 접근할 수 있습니다.");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 실행 후
        // 어드민 접근 가능 로직 실행

        // handler = Controller method
        // chosen handler to execute, for type and/or instance evaluation
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String methodName = handlerMethod.getMethod().getName();

        // deleteComment() & changeUserRole()
        if("deleteComment".equals(methodName) || "changeUserRole".equals(methodName)) {
            log.info("관리자 접근 - 요청 시각: {}, 컨트롤러 메서드: {}", System.currentTimeMillis(), methodName);
        }
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        // 요청 완료 후
//    }
}
