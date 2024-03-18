package TransactionServiceTest;
import mps2k1.bank.model.Token;
import mps2k1.bank.model.Transaction;
import mps2k1.bank.model.UserEntity;
import mps2k1.bank.repos.TransactionRepository;
import mps2k1.bank.repos.UserEntityRepository;
import mps2k1.bank.service.imp.TokenService;
import mps2k1.bank.service.imp.TransactionServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDateTime;
import java.util.UUID;
@SpringBootTest(classes = TransactionServiceImp.class)
public class TransactionServiceTest {
    private static final String TITLE = "testowy tytuł przelewu";
    private static final String LOGIN_FOR_USER1 = "test_login1";
    private static final String PASSWORD = "test_password1";
    private static final String LOGIN_FOR_USER2 = "test_login2";
    private static final String NAME_USER1 = "test_name";
    private static final String LASTNAME_USER1 = "test_lastName";
    private static final String NAME_USER2 = "test_name";
    private static final String LASTNAME_USER2 = "test_lastName";
    private static final UUID TOKEN_USER1 = UUID.fromString("0713dcaf-766d-4557-bfe8-4d7df128e360");
    private static final UUID TOKEN_USER2 = UUID.fromString("1285a6fc-684d-4961-81e2-5382bb7d016d");
    @Mock
    TransactionServiceImp transactionServiceImp;
    @MockBean
    UserEntityRepository userRepository;
    @MockBean

    TransactionRepository transactionRepository;
    @MockBean
     TokenService tokenService;
    Transaction transaction;
    UserEntity user1;
    UserEntity user2;
    Token token1;
    Token token2;
    @BeforeEach
    void setup() {
        user1 = new UserEntity();
        user1.setBalance(1000);
        user1.setPassword(PASSWORD);
        user1.setLogin(LOGIN_FOR_USER1);
        user1.setName(NAME_USER1);
        user1.setLastName(LASTNAME_USER1);

        user2 = new UserEntity();
        user2.setBalance(1000);
        user2.setPassword(PASSWORD);
        user2.setLogin(LOGIN_FOR_USER2);
        user2.setName(NAME_USER2);
        user2.setLastName(LASTNAME_USER2);

        token1 = new Token();
        token1.setTokenValue(TOKEN_USER1);
        token1.setUser(user1);

        token2 = new Token();
        token2.setTokenValue(TOKEN_USER2);
        token2.setUser(user2);

        transaction = new Transaction();
        transaction.setSum(100);
        transaction.setTimeOfSend(LocalDateTime.now());
        transaction.setToAccount(user1.getAccountNr());
        transaction.setFromAccount(user1.getAccountNr());
        transaction.setTitle(TITLE);
    }
    @Test
    void should_save_transaction() {
        Transaction savedTransaction = transactionServiceImp.saveTransaction(token1.getTokenValue(), user2.getAccountNr(), TITLE, 100.0);
        Assertions.assertNotNull(savedTransaction);
    }
}



