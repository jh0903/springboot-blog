package com.jpastudy.study.service;

import com.jpastudy.study.DataNotFoundException;
import com.jpastudy.study.domain.Member;
import com.jpastudy.study.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String username, String email, String password){
        Member member = new Member();

        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));

        memberRepository.save(member);
        return member;
    }

    public Member getMember(String username){
        Optional<Member> findMember = memberRepository.findByusername(username);
        if(findMember.isPresent()){
            return findMember.get();
        }
        else{
            throw new DataNotFoundException("member not found");
        }

    }
}
