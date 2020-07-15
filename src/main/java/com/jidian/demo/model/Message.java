package com.jidian.demo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jidian_message", schema = "public", catalog = "")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private String content;
    private Long bgid;
    private Date date;
    @OneToMany
    @JoinColumn(name = "id")
    private List<JidianCtory> ctoryList;
    @OneToMany
    @JoinColumn(name = "id")
    private List<BiddingInfo> biddingInfoList;
}
