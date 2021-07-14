package com.convenant.springbootmysql.repository;

import com.convenant.springbootmysql.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
