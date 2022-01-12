package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    @Value("#{target.username + ' ' + target.age}")
    String getUsername();//getter 이름으로 만든다.
}
