package com.qifen.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 图片的信息
 */

@Data
@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20, nullable = false)
    private Integer pictureId;//多图的唯一标识

    @Column(nullable = false)
    private Integer pictureWidth;//多图中最大宽
    @Column(nullable = false)
    private Integer pictureHeight;//多图中最大高

    @Column(length = 32, nullable = false)
    private Integer pictureOwner;//发布者id
    @Column(length = 300, nullable = false)
    private String pictureSay;//作品说说
    @Column(length = 60, nullable = false)
    private String pictureFlag;//标签
    @Column(nullable = false)
    private long pictureTime;//发布时间戳
    @Column(length = 50)
    private String picturePraise;//赞外键
    @Column
    private long pictureView;//查看次数
    @Column(length = 50)
    private String pictureComment;//评论外键
    @Column()
    private boolean pictureCanShow;//多图是否可见
    @Column()
    private String pictureNotCanShowInfo;//多图是否可见


    //发布多图的url地址(最多9张)
    @Column(nullable = false)
    private String pictureOne;
    @Column
    private String pictureTwo;
    @Column
    private String pictureThree;
    @Column
    private String pictureFour;
    @Column
    private String pictureFive;
    @Column
    private String pictureSix;
    @Column
    private String pictureSeven;
    @Column
    private String pictureEight;
    @Column
    private String pictureNine;
}
