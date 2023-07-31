package com.example.vaccination_management.security;

import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailService implements UserDetailsService {

    @Autowired
    IAccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findAccountByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Not found account with username: " + username));
        return AccountDetail.build(account);

    }
}
