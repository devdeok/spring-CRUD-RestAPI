package com.convenant.springbootmysql.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    // 도서 모델은 PK 도서명, ISBN번호 필드를 갖는다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String isbn;

    // 저자와 도서는 1:N관계이므로 한 저자가 여러 책을 쓸 수 있음
    // 물론 여러 저자가 하나의 책을 쓸 수 있지만 이번 예제에서는 배제함
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonManagedReference
    private  Author author;
    
    // 동일한 제목의 책이 도서관 하나에 다수 존재할 경우
    // 책을 여러명이 대출할 수 있음
    @JsonBackReference
    @OneToMany(mappedBy = "book",
               fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lend> lends;
}
