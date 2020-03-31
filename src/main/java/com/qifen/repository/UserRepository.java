package com.qifen.repository;

import com.qifen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserMailAndUserPassword(String userMail, String userPassword);

    User findByUserToken(String token);

    List<User> findByUserNickname(String userNickName);

    User findByUserId(Integer userId);

    User findByUserMail(String userMail);

    boolean existsUserByUserToken(String userToken);
}
