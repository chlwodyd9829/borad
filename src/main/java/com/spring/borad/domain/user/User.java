package com.spring.borad.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "USER_TABLE")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class User {

    @Id
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;
}
