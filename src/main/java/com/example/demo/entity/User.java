package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String note;
    private String password;
}
