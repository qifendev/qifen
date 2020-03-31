package com.qifen.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 存取用户看过的图册
 */

@Data
@Entity
@Table(name = "viewed")
public class Viewed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20, nullable = false)
    private Integer viewedId;//点赞操作的唯一标识
    @Column()
    private Integer viewedUserId;//点赞操作的用户id
    @Column()
    private Integer viewedPictureId;//点赞的多图id
    @Column()
    private long viewedTime;//点赞时间
}
