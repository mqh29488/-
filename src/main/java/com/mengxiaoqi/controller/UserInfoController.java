package com.mengxiaoqi.controller;
import com.mengxiaoqi.repository.UserInfoRepository;
import com.mengxiaoqi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    // 注册接口
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String phone) {
        return userInfoService.registerUser(username, password, email, phone);
    }

    // 登录接口
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String loginInput, @RequestParam String password) {
        // 检查输入是否为空或无效
        if (loginInput == null || password == null || loginInput.trim().isEmpty() || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("用户名、邮箱或手机号和密码不能为空");
        }
        boolean isSuccess;
        // 判断登录方式：通过邮箱、手机号或用户名
        if (loginInput.contains("@")) {
            // 邮箱登录
            isSuccess = userInfoService.loginByEmail(loginInput.trim(), password.trim());
        } else if (loginInput.matches("\\d{10,}")) {
            // 手机号登录
            isSuccess = userInfoService.loginByPhone(loginInput.trim(), password.trim());
        } else {
            // 用户名登录
            isSuccess = userInfoService.loginByUsername(loginInput.trim(), password.trim());
        }
        // 根据验证结果返回响应
        if (isSuccess) {
            return ResponseEntity.ok("登录成功");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败，用户名、邮箱或手机号与密码不匹配");
        }
    }
}