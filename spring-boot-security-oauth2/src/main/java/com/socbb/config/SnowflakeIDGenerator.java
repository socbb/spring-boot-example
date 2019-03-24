package com.socbb.config;

import com.socbb.utils.IDUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * jpa Snowflake ID 策略
 * create by socbb on 2019/3/15 9:17.
 */
public class SnowflakeIDGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        return IDUtils.get();
    }
}
