package com.qifen.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 存取用户的信息，本来打算写多写点，奈何技术不允许，浪费了点字段
 */

@Data
@Entity
@Table(name = "user")
//, uniqueConstraints = {@UniqueConstraint(columnNames = {"userMail"})}//唯一约束
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20, nullable = false)
    private Integer userId;//用户标识id
    @Column(length = 20)
    private String username;//用户名
    @Column(length = 20)
    private String userPassword;//密码
    @Column()
    private int userSex;//用户性别
    @Column()
    private String userAvatar;//用户头像
    @Column()
    private String userBg;//用户背景
    @Column(length = 20)
    private String userNickname;//用户昵称
    @Column(length = 300)
    private String userSignature;//用户签名
    @Column(length = 100)
    private String userMail;//用户邮箱
    @Column(length = 20)
    private String userPhone;//用户电话
    @Column(length = 50)
    private String userToken;//token
    @Column()
    private long userTokenTime;//token有效时间
    @Column()
    private long userLastLoginTime;//最后登录时间
    @Column()
    private long userSignTime;//注册时间
    @Column()
    private long userVerifyTime;//验证时间
    @Column()
    private int userVerifyCode;//验证码
    @Column()
    private long userCanLogin;//禁止用户登录时间
    @Column()
    private String userNotCanLoginInfo;//用户禁止登录的信息


}
