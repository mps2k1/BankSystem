package mps2k1.bank.service.imp;
import jakarta.transaction.Transactional;
import mps2k1.bank.exception.InsufficientFundsException;
import mps2k1.bank.exception.UnauthorizedException;
import mps2k1.bank.exception.UserNotFoundException;
import mps2k1.bank.model.Transaction;
import mps2k1.bank.model.UserEntity;
import mps2k1.bank.repos.TransactionRepository;
import mps2k1.bank.repos.UserEntityRepository;
import mps2k1.bank.service.TransactionService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class TransactionServiceImp implements TransactionService {
    private final UserEntityRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final TokenService tokenService;

    public TransactionServiceImp(UserEntityRepository userRepository, TransactionRepository transactionRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.tokenService = tokenService;
    }
    private void validateTransaction(String fromAccount, double sum) {
        Optional<UserEntity> userFromAccountOpt = userRepository.findByAccountNr(fromAccount);
        if (userFromAccountOpt.isEmpty()) {
            throw new UserNotFoundException("Account not found: " + fromAccount);
        }

        UserEntity userFromAccount = userFromAccountOpt.get();
        if (userFromAccount.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds for transaction.");
        }
    }
    @Override
    @Transactional
    public Transaction saveTransaction(UUID token, String toAccount, String title, double sum) {
        String currentUserAccountNr = getCurrentUserAccountNr(token);
        validateTransaction(currentUserAccountNr, sum);
        validateTransaction(toAccount, sum);

        UserEntity userFrom = getUserByAccountNr(currentUserAccountNr);
        UserEntity userTo = getUserByAccountNr(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(currentUserAccountNr);
        transaction.setToAccount(toAccount);
        transaction.setTitle(title);
        transaction.setTimeOfSend(LocalDateTime.now());
        transaction.setSum(sum);

        userFrom.setBalance(userFrom.getBalance() - sum);
        userTo.setBalance(userTo.getBalance() + sum);

        userRepository.save(userFrom);
        userRepository.save(userTo);

        return transactionRepository.save(transaction);
    }
    private UserEntity getUserByAccountNr(String accountNr) {
        return userRepository.findByAccountNr(accountNr)
                .orElseThrow(() -> new UserNotFoundException("User with account number " + accountNr + " not found"));
    }

    public List<Transaction> getMyTransactions(UUID token) {
        String currentUserAccountNr = getCurrentUserAccountNr(token);
        return transactionRepository.findAllByFromAccountOrToAccount(currentUserAccountNr, currentUserAccountNr);
    }

    private String getCurrentUserAccountNr(UUID token) {
        UserEntity user = tokenService.getCurrentLoggedUser(token);
        if (user == null) {
            throw new UnauthorizedException("User is not logged in");
        }
        String userAccountNr = user.getAccountNr();
        if (userAccountNr == null) {
            throw new UserNotFoundException("User account number is not set");
        }
        return userAccountNr;
    }
}