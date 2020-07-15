package com.jidian.demo.model;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jidian_biddinginfo", schema = "public", catalog = "")
public class BiddingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long did;
    private Long buid;
    private Long amount;
    private String deadline;
    private String deposit;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Device>devices;
    @OneToMany
    @JoinColumn(name = "id")
    private List<BidUnit> bidUnits;

}
