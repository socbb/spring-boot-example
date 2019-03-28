package com.socbb.dao;

import com.socbb.bean.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by socbb on 2019/3/23 22:01.
 */
@Repository
public interface PermissionDao extends JpaRepository<Permission, Long> {

    @Query("select p from Permission p where p.id in (select id from RolePermission where role.code in ?1)")
    List<Permission> findPermissionsByRoleCodes(List<String> roleCodes);

}
