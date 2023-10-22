package desafiodio.backend.services;

import desafiodio.backend.domain.transactions.Transaction;
import desafiodio.backend.domain.users.User;
import desafiodio.backend.dtos.TransactionDTO;
import desafiodio.backend.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserService userService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender  = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        this.userService.validate(sender, transactionDTO.amount());

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(transactionDTO.amount());
        transaction.setTimestamp(LocalDateTime.now());

        setAmounts(transactionDTO);

        this.repository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        return transaction;
    }

    private void setAmounts(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.amount()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.amount()));
    }

    public List<Transaction> getAllTransactions() {
        return this.repository.findAll();
    }
}
