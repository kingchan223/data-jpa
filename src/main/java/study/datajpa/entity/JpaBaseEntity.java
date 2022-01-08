package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
public class JpaBaseEntity {

    @Column(updatable = false)//생성일은 변경될 수 없다.
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist//Persist하기 전에 호출
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate//Update하기 전에 호출
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}
