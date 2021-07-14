package com.convenant.springbootmysql.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "lend")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant startOn; // 대출 시작
    private Instant dueOn; // 대출 종료
    @Enumerated(EnumType.ORDINAL)
    private LendStatus status; // 대출 상태
}
