package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@NamedQuery(name="Member.findByUsername", query="select m from Member m where m.username= :username")
@ToString
@NoArgsConstructor(access= AccessLevel.PROTECTED)/*프록시 객체를 위해 protect로 기본 생성자*/
@Getter @Setter
@Entity
public class  Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    private String username;
    private int age;

    @ToString.Exclude//연관관계 필드는 toString을 안하는 것이 좋다.
    @JoinColumn(name="team_id")
    @ManyToOne(fetch=FetchType.LAZY)
    private Team team;

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team!=null) this.changeTeam(team);
    }

    //연관관계 메서드
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public Member(String username) {
        this.username = username;
    }
}
