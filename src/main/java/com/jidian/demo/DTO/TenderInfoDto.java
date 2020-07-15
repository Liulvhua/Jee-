package com.jidian.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenderInfoDto {
    private Long id;
    private Date date;
    private Long tuid;
    private Long bgid;
    private Long cid;
    private Long did;
    private String money;
    private Date paytime;

}
