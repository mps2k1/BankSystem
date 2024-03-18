package mps2k1.bank.controller;
import mps2k1.bank.dto.TransactionRequest;
import mps2k1.bank.model.Transaction;
import mps2k1.bank.service.imp.TransactionServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionServiceImp transactionService;
    public TransactionController(TransactionServiceImp transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@RequestBody TransactionRequest request,@RequestParam UUID token) {
        try {
            Transaction transaction = transactionService.saveTransaction(
                    token,
                    request.getToAccount(),
                    request.getTitle(),
                    request.getSum());
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
