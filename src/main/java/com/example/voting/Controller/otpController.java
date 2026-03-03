package com.example.voting.Controller;

import com.example.voting.Service.JwtService;
import com.example.voting.Service.OtpService;
import com.example.voting.Service.smsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class otpController {
    @Autowired
    private OtpService otpService;
    @Autowired
    private smsService smsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String phone) {
        String otp = otpService.generateOtp();
        otpService.saveOtp(phone, otp);
        smsService.sendOtp(phone, otp);
    return ResponseEntity.ok("OTP Sent Successfully to : " + phone);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String phone,
                                       @RequestParam String otp) {

        String storedOtp = redisTemplate.opsForValue().get(phone);

        if (storedOtp != null && storedOtp.equals(otp)) {
            redisTemplate.delete(phone);
            String token = jwtService.generateToken(phone);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid OTP");
    }
}
