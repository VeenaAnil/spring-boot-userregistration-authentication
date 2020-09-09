package com.staxter.uam.repository;

import org.springframework.data.repository.CrudRepository;
/**
 * 
 * This repository interface for database operations
 * @author Veena Anil
 *
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUserName(String userName);

}
