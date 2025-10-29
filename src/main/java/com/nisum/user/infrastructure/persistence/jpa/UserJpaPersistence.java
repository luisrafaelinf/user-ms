package com.nisum.user.infrastructure.persistence.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nisum.user.infrastructure.persistence.model.User;

@Repository
public interface UserJpaPersistence extends JpaRepository<User, UUID> {

	@Query("select u from User u where u.email = :email and u.active = true")
	public Optional<User> findByUserEmailAndActive(@Param("email") String email);

	@Query("select u from User u where lower(u.email) = :email")
	public Optional<User> findByEmail(@Param("email") String email);

}
