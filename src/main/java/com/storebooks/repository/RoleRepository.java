package com.storebooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
