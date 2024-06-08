package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.RolePermission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<RolePermission, String> {
}
