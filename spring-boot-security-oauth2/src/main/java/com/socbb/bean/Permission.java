package com.socbb.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * create by socbb on 2019/3/24 10:08.
 */
@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = -4366456516829121540L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Snowflake-id")
    @GenericGenerator(name = "Snowflake-id", strategy = "com.socbb.config.SnowflakeIDGenerator")
    private Long id;

    private String permission;

    private String name;

    @Column(name = "create_time")
    private Date createTIme;
}
