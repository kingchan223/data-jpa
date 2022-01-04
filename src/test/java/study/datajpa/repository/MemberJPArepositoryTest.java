package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.*;

@Transactional/*트랜잭션*/ @Rollback(false) //자동으로 rollback시키는 것을 막음
@SpringBootTest//이거 하나면 스프링 빈을 사용할 수 있다.
class MemberJPArepositoryTest {

    @Autowired MemberJPArepository memberJPArepository;

    @Test
    void save() {
//        Member member = new Member("memberA");
//        Member saveMember = memberJPArepository.save(member);
//        Member findMember = memberJPArepository.find(saveMember.getId());
//        assertThat(member.getId()).isEqualTo(findMember.getId());
//        assertThat(saveMember.getId()).isEqualTo(findMember.getId());
//        assertThat(member).isEqualTo(findMember);
    }
}