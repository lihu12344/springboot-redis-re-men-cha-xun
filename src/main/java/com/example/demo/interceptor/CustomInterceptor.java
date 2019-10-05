package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key=request.getRequestURI();
        if(redisTemplate.boundZSetOps("热门查询").rank(key)==null){
            redisTemplate.opsForZSet().add("热门查询",key,1L);
        }else {
            redisTemplate.opsForZSet().incrementScore("热门查询",key,1L);
        }

        return true;
    }
}