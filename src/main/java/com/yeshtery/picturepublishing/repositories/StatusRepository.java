package com.yeshtery.picturepublishing.repositories;

import com.yeshtery.picturepublishing.model.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity,Integer> {
    public StatusEntity getByStatus(String status);
}
