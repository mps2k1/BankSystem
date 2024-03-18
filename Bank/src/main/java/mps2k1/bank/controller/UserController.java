package mps2k1.bank.controller;
import mps2k1.bank.model.Transaction;
import mps2k1.bank.service.imp.TransactionServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final TransactionServiceImp transactionServiceImp;
    public UserController(TransactionServiceImp transactionServiceImp) {
        this.transactionServiceImp = transactionServiceImp;
    }
    @GetMapping
    public ResponseEntity<List<Transaction>> getMyTransactions(@RequestParam UUID token) {
        try {
            List<Transaction> myTransactions = transactionServiceImp.getMyTransactions(token);
            return new ResponseEntity<>(myTransactions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
