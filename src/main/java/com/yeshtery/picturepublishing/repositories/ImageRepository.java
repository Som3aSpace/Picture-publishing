package com.yeshtery.picturepublishing.repositories;

import com.yeshtery.picturepublishing.model.entity.ImageEntity;
import com.yeshtery.picturepublishing.model.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity,Integer> {
    List<ImageEntity> getAllByStatusEntity(StatusEntity statusEntity);
    ImageEntity getById(int id);
}
