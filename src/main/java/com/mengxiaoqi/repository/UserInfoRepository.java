package com.mengxiaoqi.repository;

import com.mengxiaoqi.info.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    //通过用户名查找
    Optional<UserInfo> findByUsername(String username);
    //通过邮箱查找
    Optional<UserInfo> findByEmail(String email);
    //通过手机号查找
    Optional<UserInfo> findByPhone(String phone);
}
