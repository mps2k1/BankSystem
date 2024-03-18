package mps2k1.bank.service.imp;

import jakarta.transaction.Transactional;
import mps2k1.bank.dto.LoginRequest;
import mps2k1.bank.exception.BadCriedentialsException;
import mps2k1.bank.exception.UserNotFoundException;
import mps2k1.bank.model.Token;
import mps2k1.bank.model.UserEntity;
import mps2k1.bank.repos.TokenRepository;
import mps2k1.bank.repos.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
public class TokenService {
    private final UserEntityRepository userRepository;
    private final TokenRepository tokenRepository;

    public TokenService(UserEntityRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }
    private Token generateToken(UserEntity user) {
        Token token = new Token();
        token.setTokenValue(UUID.randomUUID());
        token.setUser(user);
        token.setIssue(LocalDateTime.now());
        token.setEnable(true);
        return tokenRepository.save(token);
    }
    @Transactional
    public Token loginUser(LoginRequest request) {
        Optional<UserEntity> user = userRepository.findByLogin(request.getUsername());
        if (user.isPresent()) {
            UserEntity userLogin = user.get();
            return generateToken(userLogin);
        } else {
            throw new BadCriedentialsException("Username or password incorrect");
        }
    }
    public UserEntity getCurrentLoggedUser(UUID token) {
        Token tokenEntity = tokenRepository.findByTokenValue(token);
        if (tokenEntity != null && tokenEntity.isEnable()) {
            return tokenEntity.getUser();
        } else {
            throw new UserNotFoundException("User not logged in...");
        }
    }
    public void logoutUser(UUID token){
        Token tokenEntity = tokenRepository.findByTokenValue(token);
        tokenEntity.setExpiration(LocalDateTime.now());
        tokenEntity.setEnable(false);
        tokenRepository.save(tokenEntity);
    }
}