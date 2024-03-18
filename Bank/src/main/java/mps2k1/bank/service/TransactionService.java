package mps2k1.bank.service;

import mps2k1.bank.model.Transaction;

import java.util.UUID;

public interface TransactionService {
Transaction saveTransaction(UUID token, String toAccount, String title, double sum);
}
