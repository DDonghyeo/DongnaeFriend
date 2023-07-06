package com.umc.DongnaeFriend;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {
    private final UserRepository userRepository;

    @GetMapping("")
    public String hi() {
        return "hih????";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "health check ok!!!";
    }
}