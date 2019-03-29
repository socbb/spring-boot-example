package com.socbb.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

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
    @GenericGenerator(name = "Snowflake-id", strategy = "com.socbb.config.SnowflakeIDGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    private String code;

    private Integer status;
}
