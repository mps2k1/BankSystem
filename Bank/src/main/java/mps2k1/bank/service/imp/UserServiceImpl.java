package mps2k1.bank.service.imp;
import jakarta.transaction.Transactional;
import mps2k1.bank.dto.UserRequest;
import mps2k1.bank.model.UserEntity;
import mps2k1.bank.repos.UserEntityRepository;
import mps2k1.bank.service.UserService;
import org.springframework.stereotype.Service;
import java.util.Random;
@Service
public class UserServiceImpl implements UserService {
    private final UserEntityRepository userRepository;

    public UserServiceImpl(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public UserEntity saveUser(UserRequest request){
        if (userRepository.existsByLogin(request.getLogin())){
            throw new RuntimeException("Username is taken");
        }
        UserEntity user = new UserEntity();
        user.setAccountNr(generateAccountNr());
        user.setBalance(100);
        user.setLogin(request.getLogin());
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
return userRepository.save(user);
    }
    private String generateAccountNr() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
}
