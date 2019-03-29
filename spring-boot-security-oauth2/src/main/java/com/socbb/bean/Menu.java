package com.socbb.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限表
 * create by socbb on 2019/3/24 10:08.
 */
@Getter
@Setter
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = -4366456516829121540L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Snowflake-id")
    @GenericGenerator(name = "Snowflake-id", strategy = "com.socbb.config.SnowflakeIDGenerator")
    private Long id;

    /**
     * 父级菜单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    private String name;

    private String icon;

    /**
     * 权限标识
     */
    private String permission;

    private String path;

    /**
     * 用户权限匹配
     */
    private String url;

    private String method;

    /**
     * 0 = 菜单
     * 1 = 按钮
     */
    private Integer type;

    /**
     * 0 = 启用
     * 1 = 禁用
     */
    private Integer status;

    @Column(name = "f_sequence")
    private Integer sequence;

    @Column(name = "create_time")
    private LocalDateTime createTIme;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @OrderBy(value = "sequence asc, id asc")
    private List<Menu> children = new ArrayList<>(0);
}
