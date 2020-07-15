package com.jidian.demo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jidian_tenderinginfo", schema = "public", catalog = "")
public class TenderingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Long tuid;
    private Long bgid;
    private Long cid;
    private Long did;
    private String money;
    private Timestamp paytime;


    @OneToMany
    @JoinColumn(name = "id")
    private List<Tenderunit> tenderunitList;

    @OneToMany
    @JoinColumn(name = "id")
    private List<BiddingInfo> biddingInfoList;

    @OneToMany
    @JoinColumn(name = "id")
    private List<JidianCtory> jidianCtoryList;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Device> deviceList;
}
