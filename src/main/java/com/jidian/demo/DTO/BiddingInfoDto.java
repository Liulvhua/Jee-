package com.jidian.demo.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiddingInfoDto {
    private Long id;
    private Long did;
    private Long buid;
    private Long amount;
    private String deadline;
    private String deposit;
}
