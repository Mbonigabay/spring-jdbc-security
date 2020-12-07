package com.example.demo.Model;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthenticationRequest implements Serializable {
    private String userName;
    private String password;
}