package com.socbb.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socbb.bean.Role;
import com.socbb.bean.User;
import com.socbb.consts.SecurityConst;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * create by socbb on 2019/3/24 9:19.
 */
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 5621029256343842696L;

    private Long id;

    private String username;

    private String password;

    private Integer status;

    private Set<Role> roles = new HashSet<>(0);

    public UserDetailsImpl(User user, Set<Role> roles) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.roles = roles;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role role : roles) {
            authorityList.add(new SimpleGrantedAuthority(role.getCode()));
        }
        authorityList.add(new SimpleGrantedAuthority(SecurityConst.BASE_ROLE));
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStatus() == 0;
    }
}
