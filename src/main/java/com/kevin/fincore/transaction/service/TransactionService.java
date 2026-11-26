package com.kevin.fincore.transaction.service;

import com.kevin.fincore.account.entity.Account;
import com.kevin.fincore.account.entity.AccountStatus;
import com.kevin.fincore.account.repository.AccountRepository;
import com.kevin.fincore.transaction.dto.TransactionRequest;
import com.kevin.fincore.transaction.dto.TransactionResponse;
import com.kevin.fincore.transaction.entity.Transaction;
import com.kevin.fincore.transaction.entity.TransactionStatus;
import com.kevin.fincore.transaction.repository.TransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService( // Responsabilidade de criar transações entre contas.
            TransactionRepository transactionRepository,
            AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionResponse createTransaction( // Cria uma transação entre duas contas.
            TransactionRequest request,
            UUID userId) {

        UUID sourceId = request.sourceAccountId();
        UUID destinationId = request.destinationAccountId();

        // Impede transferências para a mesma conta.
        if (sourceId.equals(destinationId)) {
            throw new IllegalArgumentException(
                "A conta de origem e destino não podem ser iguais."
            );
        }

        if (request.amount() == null || request.amount().signum() <= 0) {
            throw new IllegalArgumentException(
                 "O valor da transferência deve ser maior que zero."
            );
        }

        // Bloqueia as contas sempre na mesma ordem para reduzir deadlocks.
        UUID firstId = sourceId.compareTo(destinationId) < 0
                ? sourceId : destinationId;

        UUID secondId = sourceId.compareTo(destinationId) < 0
                ? destinationId : sourceId;

        Account firstAccount = accountRepository
                .findByIdWithLock(firstId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Conta não encontrada."
                ));

        Account secondAccount = accountRepository
                .findByIdWithLock(secondId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Conta não encontrada."
                ));

        // Identifica qual objeto corresponde a cada papel.
        Account sourceAccount = sourceId.equals(firstId)
                ? firstAccount : secondAccount;

        Account destinationAccount = destinationId.equals(firstId)
                ? firstAccount : secondAccount;

        // A conta de origem precisa pertencer ao usuário autenticado.
        if (!sourceAccount.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException(
                "Conta de origem não encontrada."
            );
        }

        // Verifica se as duas contas estão ativas.
        if (sourceAccount.getStatus() != AccountStatus.ACTIVE
                || destinationAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException(
                "As duas contas precisam estar ativas."
            );
        }

        // Verifica se existe saldo suficiente.
        if (sourceAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException(
                "Saldo insuficiente para realizar a transferência."
            );
        }

        // Realiza o débito e o crédito.
        sourceAccount.debit(request.amount());
        destinationAccount.credit(request.amount());

        // Registra a transferência.
        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setAmount(request.amount());
        transaction.setStatus(TransactionStatus.COMPLETED);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return TransactionResponse.fromEntity(savedTransaction);
    }
}

