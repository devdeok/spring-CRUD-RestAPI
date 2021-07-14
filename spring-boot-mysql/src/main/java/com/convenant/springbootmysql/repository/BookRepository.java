package com.convenant.springbootmysql.repository;

import com.convenant.springbootmysql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository에서 기본적으로 제공하는 기능 이외에 추가적으로 구현하고 싶은 부분이 있다면
// findBy로 시작하는 메소드 이름으로 쿼리를 요청하는 메소드임을 지정하면 됨
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
}
