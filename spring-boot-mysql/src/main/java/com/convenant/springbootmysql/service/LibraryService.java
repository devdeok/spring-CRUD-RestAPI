package com.convenant.springbootmysql.service;

import com.convenant.springbootmysql.model.*;
import com.convenant.springbootmysql.repository.AuthorRepository;
import com.convenant.springbootmysql.repository.BookRepository;
import com.convenant.springbootmysql.repository.LendRepository;
import com.convenant.springbootmysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private  final AuthorRepository authorRepository;
    private  final MemberRepository memberRepository
    private  final LendRepository lendRepository;
    private  final BookRepository bookRepository;

    // id를 기준으로 데이터베이스의 도서를 조회
    public Book readBook(Long id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            return book.get();
        }
        throw new EntityNotFoundException("Can't find any book under given ID")
    }// readBook
    
    // 데이터베이스에 저장된 모든 도서를 읽음
    public List<Book> readBooks(){
        return bookRepository.findAll();
    }// readBooks

    // isbn을 기준으로 데이터베이스의 도서를 조회
    public Book readBook(String isbn){
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if(book.isPresent()){
            return book.get();
        }
        throw new EntityNotFoundException("Can't find any book under given ISBN")
    }// readBook
    
    // BookCreationRequest로 도서를 생성
    public Book createBook(BookCreationRequest book){
        Optional<Author> author = authorRepository.findById((book.getAuthorId()));
        if(!author.isPresent()){
            throw new EntityNotFoundException("Author Not Found");
        }
        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book, bookToCreate);
        bookToCreate.setAuthor(author.get());
        return bookRepository.save(bookToCreate);
    } // createBook

    // id를 기준으로 도서를 삭제
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    } // deleteBook
    
    // MemberCreationRequest로 회원을 생성
    public Member createMember(MemberCreationRequest request){
        Member member = new Member();
        BeanUtils.copyProperties(request, member);
        return memberRepository.save(member);
    } // createMember
    
    // id에 해당하는 회원을 MemberCreation Request로 변경
    public Member updateMember (Long id, MemberCreationRequest request){
        Optional<Member> optionalMember = memberRepository.findById(id);
        if(!optionalMember.isPresent()){
            throw new EntityNotFoundException("Member not present in the database")
        }
        Member member = optionalMember.get();
        member.setLastName(request.getLastName());
        member.setFirstName(request.getFirstName());
        return memberRepository.save(member);
    }
    
    // AuthorCreationRequest로 저자를 생성
    public Author createAuthor(AuthorCreationRequest request){
        Author author = new Author();
        BeanUtils.copyProperties(request, author);
        return authorRepository.save(author);
    }
    
    // BookLendRequest로 도서를 대출
    public List<String> lendBook(List<BookLendRequest> list){
        List<String> bookApprovedToBurrow = new ArrayList<>();

        list.forEach(bookLendRequest -> {
            Optional<Book> bookForId =
                    bookRepository.findById(bookLendRequest.getBookId());
            if(!bookForId.isPresent()){
                throw new EntityNotFoundException("Can't find any book under given ID");
            }

            Optional<Member> memberForId =
                    memberRepository.findById(bookLendRequest.getMemberId());
            if(!memberForId.isPresent()){
                throw new EntityNotFoundException("Member not present in the database");
            }

            Member member = memberForId.get();
            if(member.getStatus() != MemberStatus.ACTIVE){
                throw new RuntimeException("User is not active to proceed a lending");
            }

            Optional<Lend> burrowedBook =
                    lendRepository.findByBookAndStatus(bookForId.get(),
                                                       LendStatus.BURROWED);

            if(!burrowedBook.isPresent()){
                bookApprovedToBurrow.add(bookForId.get().getName());
                Lend lend = new Lend();
                lend.setMember(memberForId.get());
                lend.setBook(bookForId.get());
                lend.setStatus(LendStatus.BURROWED);
                lend.setStartOn(Instant.now());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                lendRepository.save(lend);
            }
        });
        return bookApprovedToBurrow;
    }
}
