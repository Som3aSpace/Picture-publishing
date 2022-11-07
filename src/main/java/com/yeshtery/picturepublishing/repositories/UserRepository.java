package com.yeshtery.picturepublishing.repositories;

import com.yeshtery.picturepublishing.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity getByUserName(String username);
    UserEntity getByEmail(String email);
}
