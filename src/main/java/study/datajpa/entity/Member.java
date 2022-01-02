package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Getter @Setter
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    protected Member(){}/*private으로 하면 프록시 객체를 만들 수 없다.*/

    public Member(String username) {
        this.username = username;
    }
}
