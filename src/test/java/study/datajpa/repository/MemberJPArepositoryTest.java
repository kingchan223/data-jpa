package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional/*트랜잭션*/ @Rollback(false) //자동으로 rollback시키는 것을 막음
@SpringBootTest//이거 하나면 스프링 빈을 사용할 수 있다.
class MemberJPArepositoryTest {

    @Autowired MemberJPArepository memberJPArepository;
    @Autowired MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member("memberA");
        Member saveMember = memberJPArepository.save(member);
        Member findMember = memberJPArepository.find(saveMember.getId());
        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(saveMember.getId()).isEqualTo(findMember.getId());
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        //단건 조회 기능
        memberJPArepository.save(member1);
        memberJPArepository.save(member2);
        Member findMember1 = memberJPArepository.findById(member1.getId()).get();
        Member findMember2 = memberJPArepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검즘
        List<Member> all = memberJPArepository.findAll();
        assertThat(all.size()).isEqualTo(2L);

        long count = memberJPArepository.count();
        assertThat(count).isEqualTo(2L);

        memberJPArepository.delete(findMember1);
        memberJPArepository.delete(findMember2);
        long count2 = memberJPArepository.count();
        assertThat(count2).isEqualTo(0);
    }

    @Test
    public void basic_DATA_JPA_CRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        //단건 조회 기능
        memberRepository.save(member1);
        memberRepository.save(member2);
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검즘
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2L);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2L);

        memberRepository.delete(findMember1);
        memberRepository.delete(findMember2);
        long count2 = memberRepository.count();
        assertThat(count2).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan(){
        Member member1 = new Member("member1", 10, null);
        Member member2 = new Member("member2", 13, null);
        memberJPArepository.save(member1);
        memberJPArepository.save(member2);

        Member findMember = memberJPArepository.findByUsernameAndAgeGreaterThan("member2", 10).get(0);

        assertThat(findMember).isEqualTo(member2);
        assertThat(findMember.getAge()).isEqualTo(13);
        assertThat(findMember.getUsername()).isEqualTo("member2");
    }

    @Test
    void testNamedQuery(){
        Member member1 = new Member("member1", 10, null);
        memberJPArepository.save(member1);

        Member findMember = memberJPArepository.findByUsername("member1").get(0);

        assertThat(findMember).isEqualTo(member1);
        assertThat(findMember.getAge()).isEqualTo(10);
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void jpaFindByUsername(){
        Member member1 = new Member("member1", 10, null);
        memberJPArepository.save(member1);
        Member findMember = memberJPArepository.findByUsername("member1").get(0);
    }

    @Test
    void pagingByAgeTest(){
        int age = 20;
        int offset = 0;
        int limit = 3;

        //10살인 멤버 1명
        Member member10 = new Member("member", 10, null);
        memberJPArepository.save(member10);

        //20살인 멤버 30명
        for(int i=0; i<30; i++){
            Member member = new Member("member"+i, 20, null);
            memberJPArepository.save(member);
        }
        long numOf20Member = memberJPArepository.totalCount(age);
        assertThat(numOf20Member).isEqualTo(30L);//20살인 멤버 30명 맞나?

        List<Member> members = memberJPArepository.findByPage(age, offset, limit);
        assertThat(members.size()).isEqualTo(limit);//가져온 페이징 목록 3명의 멤버?
    }

    @Test
    void bulkUpdateAgeTest(){
        //given
        int age = 20;
        //20살인 멤버 30명
        for(int i=0; i<30; i++){
            Member member = new Member("member"+i, i, null);
            memberJPArepository.save(member);
        }

        //when
        int resultCount = memberJPArepository.bulkAgePlus(age);

        //then
        assertThat(resultCount).isEqualTo(10);
    }
}
