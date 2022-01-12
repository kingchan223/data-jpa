package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.entity.dto.MemberDto;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional @Rollback(false)
@SpringBootTest
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager em;

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

    @Test
    void bulkUpdateAgeTest(){
        //given
        int age = 20;
        //20살인 멤버 30명
        for(int i=0; i<30; i++){
            Member member = new Member("member"+i, i, null);
            memberRepository.save(member);
        }

        //when
        int resultCount = memberRepository.bulkAgePlus(age);

        //then
        assertThat(resultCount).isEqualTo(10);
    }

    @Test
    void queryBeforeHint() {
        //given
        Member member1 = memberRepository.save(new Member("member1", 10, null));

        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findById(member1.getId()).get();
        findMember.setUsername("member2");//member1의 이름 수정

        em.flush();//Dirty checking을 하여 바뀐것이 있다면 Update 쿼리를 날린다.
        /* Dirty checking의 단점은 영속성 컨텍스트에 두 개의 객체를 가지고 있어야 한다는 것이다.*/
        /* 즉 member1과 member1의 스냅샷을 가지고 있어야 한다. --> 메모리를 더 먹게된다. */
        /* 하여 개발자가 member 변경은 원하지 않고, 단순히 조회만 원할 때에도 메모리에 두개의 객체가 생기기 때문에*/
        /* 이는 커다란 단점이라 할 수 있다. */
    }

    @Test
    void queryHint() {
        //given
        Member member1 = memberRepository.save(new Member("member1", 10, null));

        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");//member1의 이름 수정

        em.flush();
    }

    @Test
    void lock() {
        //given
        Member member1 = memberRepository.save(new Member("member1", 10, null));

        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findLockByUsername("member1");
    }

    @Test
    void callCustom() {
        //given
        memberRepository.save(new Member("member1", 10, null));
        memberRepository.save(new Member("member2", 20, null));

        List<Member> members = memberRepository.findMemberCustom();
    }

    @Test
    void JpaEventBaseEntity() throws Exception {
        //given
        Member  member = new Member("member1", 10, null);
        memberRepository.save(member); /* @PrePersist 발동 */

        Thread.sleep(1000);//1초 지연
        member.setUsername("멤버1");

        em.flush(); /* @PreUpdate 발동*/
        em.clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        //then
        System.out.println("findMember.getCreatedDate() = " + findMember.getCreatedDate());
        System.out.println("findMember.getLastModifiedDate() = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreatedBy() = " + findMember.getCreatedBy());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
    }

    @Test
    public void specBasic(){
        //--given--
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);
        em.flush();
        em.clear();

        //--when--
        //username == m1 이고, teamName == teamA 인 Member
        Specification<Member> spec = MemberSpec.username("m1").and(MemberSpec.teamName("teamA"));
        List<Member> result = memberRepository.findAll(spec);

        //--then--
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void queryBuExample() {
        //--given--
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);
        em.flush();
        em.clear();

        //--when--
        //Probe
        Member member = new Member("m1"); //엔티티 자체가 검색조건이 된다.

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("age");//age는 무시
        Example<Member> example = Example.of(member,matcher);//member와 matcher로 example 생성
        List<Member> result = memberRepository.findAll(example);

        //--then--
        assertThat(result.get(0).getUsername()).isEqualTo(m1.getUsername());
    }

    //join에서 완변한 해결이 안된다.
    @Test
    void queryBuExample2() {
        //--given--
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);
        em.flush();
        em.clear();

        //--when--
        //Probe 생성
        Member member = new Member("m1"); //엔티티 자체가 검색조건이 된다.
        Team team = new Team("teamA");
        member.setTeam(team); //연관관계를 세팅

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("age");//age는 무시
        Example<Member> example = Example.of(member,matcher);//member와 matcher로 example 생성
        List<Member> result = memberRepository.findAll(example);

        //--then--
        assertThat(result.get(0).getUsername()).isEqualTo(m1.getUsername());
    }

    @Test
    void projections() {
        //--given--
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);
        em.flush();
        em.clear();

        //--when--
        List<UsernameOnlyDto> result = memberRepository.findProjectionsByUsername("m1");

        //--then--
        System.out.println(result.get(0));
    }

}
