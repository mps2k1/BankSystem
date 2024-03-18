package mps2k1.bank.repos;

import mps2k1.bank.model.Token;
import mps2k1.bank.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findByTokenValue(UUID token);

    Token findByUser(UserEntity userEntity);
}
