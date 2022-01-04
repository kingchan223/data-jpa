package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor(access= AccessLevel.PROTECTED)/*프록시 객체를 위해 protect로 기본 생성자*/
@Getter @Setter
@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name="team_id")
    private Long id;
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy="team", cascade=CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
