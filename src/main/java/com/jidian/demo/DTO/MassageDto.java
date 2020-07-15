package com.jidian.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MassageDto {
    private Long id;
    private Long cid;
    private String name;
    private String content;
    private Long bgid;
    private Date date;
}
