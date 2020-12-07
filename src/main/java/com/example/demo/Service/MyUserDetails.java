package com.example.demo.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.Model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = 7051503553644208680L;
    /**
     *
     */
    private String email;
    private String password;
    private Boolean active;
    private User user;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(User user) {
        this.password = user.getPassword();
        this.active = user.getActive();
        this.user = user;
        this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public MyUserDetails() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return email;
    }

    @Override
    public boolean isAccountNonExpired() {

        return active;
    }

    @Override
    public boolean isAccountNonLocked() {

        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return active;
    }

    @Override
    public boolean isEnabled() {

        return active;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
