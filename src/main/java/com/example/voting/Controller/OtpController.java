package com.example.voting.Controller;

import com.example.voting.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {

        otpService.generateOtp(email);

        return "OTP sent to email";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {

        boolean result = otpService.verifyOtp(email, otp);

        if (result) {
            return "OTP verified successfully";
        }

        return "Invalid or expired OTP";
    }
}
