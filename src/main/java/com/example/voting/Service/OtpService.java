package com.example.voting.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailService emailService;

    public String generateOtp(String email) {

        int otp = 100000 + new Random().nextInt(900000);
        String otpValue = String.valueOf(otp);

        String key = "OTP:" + email;

        redisTemplate.opsForValue().set(key, otpValue, 2, TimeUnit.MINUTES);

        emailService.sendOtpEmail(email, otpValue);

        return otpValue;
    }

    public boolean verifyOtp(String email, String otp) {

        String key = "OTP:" + email;

        String storedOtp = redisTemplate.opsForValue().get(key);

        if (storedOtp == null) {
            return false;
        }

        if (storedOtp.equals(otp)) {

            redisTemplate.delete(key);
            return true;

        }

        return false;
    }
}