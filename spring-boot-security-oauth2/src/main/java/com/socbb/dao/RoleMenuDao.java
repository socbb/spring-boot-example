package com.socbb.dao;

import com.socbb.bean.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * create by socbb on 2019/3/23 22:01.
 */
@Repository
public interface RoleMenuDao extends JpaRepository<RoleMenu, Long>, JpaSpecificationExecutor<RoleMenu> {
}
