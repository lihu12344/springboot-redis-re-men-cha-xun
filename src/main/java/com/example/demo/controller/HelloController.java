package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class HelloController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/hello/{id}")
    public String hello(@PathVariable("id") Integer id){
        System.out.println(id);

        return "hello world";
    }

    @RequestMapping("/hello2/{id}")
    public String hello2(@PathVariable("id") Integer id){
        return "hello world2";
    }

    @RequestMapping("/test")
    public String test(){
        redisTemplate.opsForZSet().add("zset","瓜田李下",3L);
        redisTemplate.opsForZSet().add("zset","瓜田李下 2",2L);
        System.out.println(redisTemplate.opsForZSet().count("zset",2L,3L));
        System.out.println(redisTemplate.opsForZSet().rank("zset","瓜田李下"));
        System.out.println(redisTemplate.opsForZSet().rank("zset","瓜田李下 2"));

        return "success";
    }

    @RequestMapping("/get")
    public String get(){
        Set<ZSetOperations.TypedTuple<String>> set=redisTemplate.opsForZSet().reverseRangeWithScores("热门查询",0L,10L);
        assert set != null;
        for (ZSetOperations.TypedTuple<String> i : set) {
            System.out.println(i.getValue() + "  " + i.getScore());
        }

        return "success";
    }
}