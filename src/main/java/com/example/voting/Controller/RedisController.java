package com.example.voting.Controller;

import com.example.voting.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/set")
    public String setValue() {
        redisService.saveData("name", "Kishore");
        return "Saved";
    }

    @GetMapping("/get")
    public Object getValue() {
        return redisService.getData("name");
    }
}
