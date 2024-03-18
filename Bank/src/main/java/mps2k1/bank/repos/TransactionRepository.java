package mps2k1.bank.repos;

import mps2k1.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByFromAccountOrToAccount(String currentUserAccountNr, String currentUserAccountNr1);
}
