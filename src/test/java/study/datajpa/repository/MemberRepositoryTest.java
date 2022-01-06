package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.entity.dto.MemberDto;


import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
//        Member member1 = new Member("member1", 10, null);
//        Member member2 = new Member("member2", 13, null);
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        Member findMember = memberRepository.findByUsername("member2").get(0);
//
//        assertThat(findMember).isEqualTo(member2);
//        assertThat(findMember.getAge()).isEqualTo(13);
//        assertThat(findMember.getUsername()).isEqualTo("member2");
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

    @Test
    void findMember(){
        Member member1 = new Member("member1", 10, null);
        Member member2 = new Member("member2", 10, null);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findListByUsername("member1");
        Member findMember = memberRepository.findMemberByUsername("member1");
        Optional<Member> optionalMember = memberRepository.findOptionalByUsername("member1");

        for (Member member : members) System.out.println(member);
        System.out.println(findMember);
        System.out.println(optionalMember);
    }

    @Test
    void pagingByAgeTest(){
        memberRepository.save(new Member("member1", 10, null));
        memberRepository.save(new Member("member2", 10, null));
        memberRepository.save(new Member("member3", 10, null));
        memberRepository.save(new Member("member4", 10, null));
        memberRepository.save(new Member("member5", 10, null));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "username");
        Page<Member> page = memberRepository.findByAge(age, pageRequest);//totalCount까지 같이 날린다.

        //조회한 member들
        List<Member> members = page.getContent();
        for (Member member : members) System.out.println(member);

        //총 데이터 개수
        long totalElements = page.getTotalElements();
        System.out.println("totalElements = " + totalElements);

        //현재 페이지
        int number = page.getNumber();
        System.out.println("number = " + number);

        //총 페이지
        int totalPages = page.getTotalPages();
        System.out.println("totalPages = " + totalPages);

        //현재 첫 페이지인가?
        boolean isFirst = page.isFirst();
        System.out.println("isFirst = " + isFirst);

        //다음 페이지가 있는가?
        boolean hasNext = page.hasNext();
        System.out.println("hasNext = " + hasNext);
    }

    @Test
    void pagingSliceByAgeTest(){
//        memberRepository.save(new Member("member1", 10, null));
//        memberRepository.save(new Member("member2", 10, null));
//        memberRepository.save(new Member("member3", 10, null));
//        memberRepository.save(new Member("member4", 10, null));
//        memberRepository.save(new Member("member5", 10, null));
//
//        int age = 10;
//        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "username");
//        Slice<Member> slice = memberRepository.findByAge(age, pageRequest);//totalCount까지 같이 날린다.
//
//        //조회한 member들
//        List<Member> members = slice.getContent();
//        for (Member member : members) System.out.println(member);
//
//        //현재 페이지
//        int number = slice.getNumber();
//        System.out.println("number = " + number);
//
//        //현재 첫 페이지인가?
//        boolean isFirst = slice.isFirst();
//        System.out.println("isFirst = " + isFirst);
//
//        //다음 페이지가 있는가?
//        boolean hasNext = slice.hasNext();
//        System.out.println("hasNext = " + hasNext);
    }

    @Test
    void pagingCountQueryTest(){
        memberRepository.save(new Member("member1", 10, null));
        memberRepository.save(new Member("member2", 10, null));
        memberRepository.save(new Member("member3", 10, null));
        memberRepository.save(new Member("member4", 10, null));
        memberRepository.save(new Member("member5", 10, null));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "username");
        Page<Member> page = memberRepository.findByAge(age, pageRequest);//totalCount까지 같이 날린다.

        //조회한 member들
        List<Member> members = page.getContent();
        for (Member member : members) System.out.println(member);
    }

    @Test
    void pagingMemberToDTO(){
        memberRepository.save(new Member("member1", 10, null));
        memberRepository.save(new Member("member2", 10, null));
        memberRepository.save(new Member("member3", 10, null));
        memberRepository.save(new Member("member4", 10, null));
        memberRepository.save(new Member("member5", 10, null));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "username");
        Page<Member> page = memberRepository.findByAge(age, pageRequest);//totalCount까지 같이 날린다.

        // DTO로 쉽게 변환하기
        Page<MemberDto> tpMap = page.map((m) ->
                new MemberDto(m.getId(), m.getUsername(), m.getTeam().getName()));

    }
}

