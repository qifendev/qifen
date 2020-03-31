package com.qifen.repository;

import com.qifen.model.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {

    Picture findByPictureId(Integer pictureId);

    Page<Picture> findByPictureOwner(Integer pictureOwner, Pageable pageable);

    Page<Picture> findByPictureSayLike(String pictureSay, Pageable pageable);


}
