package com.convenant.springbootmysql.repository;

import com.convenant.springbootmysql.model.Book;
import com.convenant.springbootmysql.model.Lend;
import com.convenant.springbootmysql.model.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 여러 필드를 검색하고 싶다면 And로 연결하면 됨.
// findByBookAndStatus는 Book과 Status필드를 검색함
public interface LendRepository extends JpaRepository<Lend, Long> {
    Optional<Lend> findByBookAndStatus(Book book, LendStatus status);
}
