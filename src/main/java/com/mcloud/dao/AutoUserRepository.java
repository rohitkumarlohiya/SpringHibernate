package com.mcloud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcloud.entities.CustomUser;

@Repository
public interface AutoUserRepository extends JpaRepository<CustomUser, Long> {

	public CustomUser findByUsername(String username);
}
