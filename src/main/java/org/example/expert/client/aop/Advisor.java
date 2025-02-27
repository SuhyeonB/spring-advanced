package org.example.expert.client.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
@Slf4j
@Aspect
@Component
public class Advisor {

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment(..)) || " +
            "execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public Object logAdminAccess(ProceedingJoinPoint joinPoint) throws Throwable {

        //Object[] args = joinPoint.getArgs();  // 파라미터 없으므로 무의미(특히 deleteComment)

        // jwtFilter에서 setAttribute로 저장한 유저정보 가져오기 위함
        // RequestContextHolder: Spring context에서 HttpServletRequest로 직접 접근할 수 있도록 도움
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Long userId = (Long) request.getAttribute("userId");
            String requestUrl = request.getRequestURI();

            log.info("관리자 ID: {}", userId);
            log.info("요청 시각: {}", LocalDateTime.now());
            log.info("요청 URL: {}", requestUrl);
        }

        log.info("요청 본문: {}", joinPoint.getTarget());

        Object responseBody = joinPoint.proceed();
        log.info("응답 본문: {}", responseBody);
        return responseBody;
    }
}
