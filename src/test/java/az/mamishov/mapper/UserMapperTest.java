package az.mamishov.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void findByUsername() {

        var user = userMapper.findByUsername("user");
        System.out.println(user.get());
    }
}