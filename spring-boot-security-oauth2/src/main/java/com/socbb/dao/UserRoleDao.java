package com.socbb.dao;

import com.socbb.bean.Role;
import com.socbb.bean.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * create by socbb on 2019/3/23 22:01.
 */
@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Long> {

    @Query("select r from Role r where r.id in (select u.role.id from UserRole u where u.user.id = ?1)")
    Set<Role> findRolesByUserId(Long userId);

}
