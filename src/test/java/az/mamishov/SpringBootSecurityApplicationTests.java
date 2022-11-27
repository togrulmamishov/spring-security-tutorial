package az.mamishov;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringBootSecurityApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void testPassword() {
        String encodedPassword = "$2a$15$SbD8Z16Di6atE9nuN2s.O.xJ/ogk9dpuy3YAWEg1DsZ4Vkx0kmtEC";
        String password1  = "togrul123";
        System.out.println(passwordEncoder.encode("pass"));
    }

}
