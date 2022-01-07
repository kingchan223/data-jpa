package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;
import study.datajpa.entity.dto.MemberDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    Member findByUsername(String username);
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

//    @Query(name="Member.findByUsername")
//    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findMember(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //DTO로 조회하기
    @Query("select new study.datajpa.entity.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    //컬렉션 조회
    List<Member> findListByUsername(String username); /*리스트는 절대 Null아님!*/

    //단건 조회
    Member findMemberByUsername(String username);

    //단건 Optional 조회
    Optional<Member> findOptionalByUsername(String username);

    @Query(value = "select m from Member m left join m.team", countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

//    Slice<Member> findByAge(int age, Pageable pageable);//Slice 사용!

//    clearAutomatically=true를 하면 영속성 컨텍스틑 비워준다.
    @Modifying(clearAutomatically = true) // @Modifying이 있어야 순수 JPA에서 본 executeUpdate를 실행시켜준다.
    @Query("update Member m set m.age = m.age + 1 where m.age  >= :age")
    int bulkAgePlus(@Param("age") int age);
}
