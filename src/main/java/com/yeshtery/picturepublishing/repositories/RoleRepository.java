package com.yeshtery.picturepublishing.repositories;

import com.yeshtery.picturepublishing.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
    RoleEntity getByName(String name);
}
