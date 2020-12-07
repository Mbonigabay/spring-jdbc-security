package com.example.demo.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean active;
    private String password;
    private String roles;
}
