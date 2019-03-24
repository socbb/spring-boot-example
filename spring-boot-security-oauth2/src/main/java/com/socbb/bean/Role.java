package com.socbb.bean;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create by socbb on 2019/3/23 21:51.
 */
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 6010360818788471039L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Snowflake-id")
    @GenericGenerator(name = "Snowflake-id", strategy = "com.com.socbb.config.SnowflakeIDGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    private String code;

    private Integer status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "role")
    private Set<RolePermission> rolePermissions = new HashSet<>(0);

    @Transient
    public Set<Permission> getPermissions() {
        Set<RolePermission> rolePermissions = getRolePermissions();
        Set<Permission> permissions = new HashSet<>(rolePermissions.size());
        if (CollectionUtils.isNotEmpty(rolePermissions)) {
            permissions = rolePermissions.stream().map(RolePermission::getPermission).collect(Collectors.toSet());
        }
        return permissions;
    }

    @Transient
    public Set<String> getPermissionStr() {
        Set<RolePermission> rolePermissions = getRolePermissions();
        Set<String> permissions = new HashSet<>(rolePermissions.size());
        if (CollectionUtils.isNotEmpty(rolePermissions)) {
            permissions = rolePermissions.stream().map(RolePermission::getPermission).map(Permission::getPermission).collect(Collectors.toSet());
        }
        return permissions;
    }
}
