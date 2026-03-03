package com.example.voting.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    public String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void saveOtp(String phone, String otp) {
        redisTemplate.opsForValue().set(phone, otp, 5, TimeUnit.MINUTES);
    }
}
