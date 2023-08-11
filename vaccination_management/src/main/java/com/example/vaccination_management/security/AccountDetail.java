package com.example.vaccination_management.security;

import com.example.vaccination_management.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDetail implements UserDetails {

    private Integer id;
    private String username;
    private Boolean enable;
    @JsonIgnore
    private String password;
    List<GrantedAuthority> authorities;

    public AccountDetail() {
    }

    public AccountDetail(Integer id, String username, Boolean enable, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.enable = enable;
        this.password = password;
        this.authorities = authorities;
    }

    public static AccountDetail build(Account account) {
        List<GrantedAuthority> authorities = account.getAccountRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toList());
        return new AccountDetail(
                account.getId(),
                account.getUserName(),
                account.getEnableFlag(),
                account.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

}
