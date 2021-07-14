package com.convenant.springbootmysql.repository;

import com.convenant.springbootmysql.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 활용하기 위해 JpaRepository에 entity와 id를 지정해주어야 함
// 나머지 Book, Lend, Member는 코드의 반복
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
