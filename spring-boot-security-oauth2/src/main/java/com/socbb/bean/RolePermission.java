package com.socbb.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * create by socbb on 2019/3/24 10:45.
 */
@Getter
@Setter
@Entity
@Table(name = "role_permission")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 4022740878023401455L;

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

}
