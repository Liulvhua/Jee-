package com.jidian.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jidian_admin", schema = "public", catalog = "")
public class Admin {
    @Id
    private Long id;
    private String username;
    @JsonIgnore
    private String password;

}
