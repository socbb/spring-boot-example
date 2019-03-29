package com.socbb.dao;

import com.socbb.bean.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by socbb on 2019/3/23 22:01.
 */
@Repository
public interface MenuDao extends JpaRepository<Menu, Long> {

    @Query("select p from Menu p where p.id in (select id from RoleMenu where role.code in ?1)")
    List<Menu> findMenusByRoleCodes(List<String> roleCodes);

}
