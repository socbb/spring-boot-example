package com.socbb.dao;

import com.socbb.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * create by socbb on 2019/3/23 22:01.
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
