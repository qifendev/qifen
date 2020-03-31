package com.qifen.repository;

import com.qifen.model.Viewed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewedRepository extends JpaRepository<Viewed, Integer> {
    boolean existsByViewedUserIdAndViewedPictureId(Integer viewedUserId, Integer viewedPictureId);
}
