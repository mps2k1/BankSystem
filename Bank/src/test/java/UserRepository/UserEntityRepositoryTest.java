package UserRepository;
import mps2k1.bank.model.UserEntity;
import mps2k1.bank.repos.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(classes = UserEntityRepository.class)
public class UserEntityRepositoryTest {
    private static final String LOGIN_FOR_USER1 = "test_login1";
    private static final String PASSWORD = "test_password1";
    private static final String NAME_USER1 = "test_name";
    private static final String LASTNAME_USER1 = "test_lastName";
    @Autowired
    UserEntityRepository userRepository;
    @Test
    void should_save_user(){
        UserEntity user = new UserEntity();
        user.setLastName(LASTNAME_USER1);
        user.setName(NAME_USER1);
        user.setPassword(PASSWORD);
        user.setLogin(LOGIN_FOR_USER1);
        UserEntity savedUser = userRepository.save(user);
        Assertions.assertNotNull(savedUser);
    }
}

