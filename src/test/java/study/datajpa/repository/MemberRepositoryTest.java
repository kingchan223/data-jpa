package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.entity.dto.MemberDto;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional @Rollback(false)
@SpringBootTest
class MemberRepositoryTest {

    MemberRepository memberRepository;
    TeamRepository teamRepository;

    @Autowired//생략가능
    public MemberRepositoryTest(MemberRepository memberRepository, TeamRepository teamRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

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

    @Test
    void testNamedQuery(){
        Member member1 = new Member("member1", 10, null);
        Member member2 = new Member("member2", 13, null);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember = memberRepository.findByUsername("member2").get(0);

        assertThat(findMember).isEqualTo(member2);
        assertThat(findMember.getAge()).isEqualTo(13);
        assertThat(findMember.getUsername()).isEqualTo("member2");
    }

    @Test
    void testQuery(){
        Member member1 = new Member("member1", 10, null);
        memberRepository.save(member1);

        Member findMember = memberRepository.findMember("member1", 10).get(0);

        assertThat(findMember).isEqualTo(member1);
        assertThat(findMember.getAge()).isEqualTo(10);
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void findUsernameList(){
        Member member1 = new Member("member1", 10, null);
        Member member2 = new Member("member2", 10, null);
        Member member3 = new Member("member3", 10, null);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String username : usernameList) System.out.println(username);
    }

    @Test
    void findMemberDto(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        new Member("member1", 10, teamA);
        new Member("member2", 10, teamA);
        new Member("member3", 10, teamB);

        teamRepository.save(teamA);// cascade로 member들도 persis된다.
        teamRepository.save(teamB);

        List<MemberDto> memberDtoList = memberRepository.findMemberDto();
        for (MemberDto memberDto : memberDtoList) System.out.println(memberDto);
    }

    @Test
    void findByNames(){
        Member member1 = new Member("member1", 10, null);
        Member member2 = new Member("member2", 10, null);
        Member member3 = new Member("member3", 10, null);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> members = memberRepository.findByNames(Arrays.asList("member1",  "member2"));
        for (Member member : members) System.out.println(member);
    }
}

