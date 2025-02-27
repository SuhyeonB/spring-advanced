package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        // given
        User user = new User("example@email.com", "password", UserRole.USER);
        userRepository.save(user);

        // when
        User comparison = userRepository.findByEmail("example@email.com").orElse(null);

        // then
        assertNotNull(comparison);
        assertEquals(comparison, user);
    }

    @Test
    @DisplayName("fail of findByEmail")
    void findByEmail_fail() {
        // given
        User user = new User("example@email.com", "password", UserRole.USER);
        userRepository.save(user);

        // when
        User comparison = userRepository.findByEmail("exmple@email.com").orElse(null);

        // then
        assertNotNull(comparison);  // expected: not<null>
        assertEquals(comparison, user);
    }

    /*
     existsByEmail은 email로 존재 여부를 확인하는 메소드로 위의 findByEmail 내에 구현 되어 있으므로 제외
     오히려 findByEmail이 객체를 반환할 수 없음(void)
     */
}