package mps2k1.bank.repos;

import mps2k1.bank.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByAccountNr(String accountNr);

    boolean existsByLogin(String login);

    Optional<UserEntity> findByLogin(String login);
}
