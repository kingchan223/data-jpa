package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 얘가 있어야 @CreatedDate 사용 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //jpa는 기본 생성자가 반드시 있어야 한다.
@Entity
public class Item implements Persistable<String> {//Persistable<id 타입>

    @Id //@GeneratedValue 없음.
    private String id;
    private String name;

    @CreatedDate
    private LocalDateTime createdDate;

    public Item(String id) {this.id = id;}

    @Override    //getId
    public String getId() {return id;}

    @Override// isNew 를 구현해야한다.
    public boolean isNew() {
        return createdDate == null;//생성일이 null이면 새로운 데이터
    }
}
