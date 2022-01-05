package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;


import static org.assertj.core.api.Assertions.assertThat;


@Transactional @Rollback(false)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testMember(){
//        Member member = new Member("memberA");
//        Member saveMember = memberRepository.save(member);
//        Member findMember = memberRepository.findById(saveMember.getId()).orElseGet(()->new Member("nullMember"));
//        assertThat(member.getId()).isEqualTo(findMember.getId());
//        assertThat(saveMember.getId()).isEqualTo(findMember.getId());
//        assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findByUsernameAndAgeGreaterThan(){
        Member member1 = new Member("member1", 10, null);
        Member member2 = new Member("member2", 13, null);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember = memberRepository.findByUsernameAndAgeGreaterThan("member2", 10).get(0);

        assertThat(findMember).isEqualTo(member2);
        assertThat(findMember.getAge()).isEqualTo(13);
        assertThat(findMember.getUsername()).isEqualTo("member2");
    }
}