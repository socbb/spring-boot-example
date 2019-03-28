package com.socbb.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * create by socbb on 2019/3/23 21:47.
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 7810063029388150587L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Snowflake-id")
    @GenericGenerator(name = "Snowflake-id", strategy = "com.socbb.config.SnowflakeIDGenerator")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    private Integer status;

    /**
     * 用户类型: 0=管理员, 1=普通用户
     */
    private Integer type;

    /**
     * 用户对角色  一对多
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "user")
    private Set<UserRole> userRoles = new HashSet<>(0);


}
