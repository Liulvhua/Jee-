package com.jidian.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JidiancDto {
    private Long id;
    private  String username;
    private String password;
    private String address;
    private Date borndate;
}
