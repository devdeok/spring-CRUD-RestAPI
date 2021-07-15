package com.convenant.springbootmysql.controller;

import com.convenant.springbootmysql.model.Author;
import com.convenant.springbootmysql.model.Book;
import com.convenant.springbootmysql.model.Member;
import com.convenant.springbootmysql.model.request.AuthorCreationRequest;
import com.convenant.springbootmysql.model.request.BookCreationRequest;
import com.convenant.springbootmysql.model.request.BookLendRequest;
import com.convenant.springbootmysql.model.request.MemberCreationRequest;
import com.convenant.springbootmysql.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller에서는 직접 Repository를 호출하지 않고 Service를 거쳐 호출
 * 처리 결과에 따라 응답값을 반환
 *
 * 주의!! 실제 서비스를 개발할 때는 Repository에서 반환하는 ResponseEntity를 응답값으로 반환하면 안됨
 * RepositoryEntity 스팩이 변경되면 API의 응답값이 변경되기 때문
 * 조회한 객체를 응답값으로 매핑하는 로직이 필요하지만 해당 예제에서는 생략
 */
@RestController
@RequestMapping(value = "/api/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/book")
    public ResponseEntity readBooks(@RequestParam(required = false) String isbn){
        if(isbn == null)
            return ResponseEntity.ok(libraryService.readBooks());
        return ResponseEntity.ok(libraryService.readBook(isbn));
    }// readBooks

    // REST API의 설계 규칙
    // {Collection}/{집합 번호}
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> readBook(@PathVariable Long bookId){
        return ResponseEntity.ok(libraryService.readBook(bookId));
    }// readBook

    @PostMapping("/book")
    public ResponseEntity<Book> createBook (@RequestBody BookCreationRequest request){
        return ResponseEntity.ok(libraryService.createBook(request));
    }// createBook

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteBook (@PathVariable Long bookId){
        libraryService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }// deleteBook

    // 회원 생성 API
    @PostMapping("/member")
    public ResponseEntity<Member> createMember(@RequestBody MemberCreationRequest request){
        return ResponseEntity.ok(libraryService.createMember(request));
    }// createMember

    @PatchMapping("/member/{memberId}")
    public ResponseEntity<Member> updateMember(@RequestBody MemberCreationRequest request,
                                               @PathVariable Long memberId){
        return ResponseEntity.ok(libraryService.updateMember(memberId,request));
    }// updateMember

    // 도서 대출 API
    @PostMapping("/book/lend")
    public ResponseEntity<List<String>> lendABook(@RequestBody BookLendRequest bookLendRequests){
        return ResponseEntity.ok(libraryService.lendABook(bookLendRequests));
    }// lendABook

    // 저자 추가 API
    @PostMapping("/author")
    public ResponseEntity<Author> createAuthor (@RequestBody AuthorCreationRequest request){
        return ResponseEntity.ok(libraryService.createAuthor(request));
    }// createAuthor





}
