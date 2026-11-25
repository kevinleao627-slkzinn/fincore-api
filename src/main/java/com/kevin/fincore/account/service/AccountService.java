package com.kevin.fincore.account.service;

import com.kevin.fincore.account.entity.Account;
import com.kevin.fincore.account.repository.AccountRepository;
import com.kevin.fincore.user.entity.User;
import com.kevin.fincore.user.repository.UserRepository;
import com.kevin.fincore.common.exception.UserNotFoundException;
import com.kevin.fincore.account.exception.AccountNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService { // Responsavel por criar e listar contas de usuários

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService( // Responsável por injetar as dependências do repositório de contas e do repositório de usuários no serviço de contas.
            AccountRepository accountRepository,
            UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Account createAccount(UUID userId) { //Responsável por criar uma nova conta para um usuário específico, identificado pelo seu ID. Se o usuário não for encontrado, lança uma exceção UserNotFoundException.

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Account account = new Account();
        account.setUser(user);

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Account> getMyAccounts(UUID userId) { // Responsável por recuperar todas as contas associadas a um usuário específico, identificado pelo seu ID. Retorna uma lista de contas do usuário.
        return accountRepository.findAllByUserId(userId);
    }
    @Transactional(readOnly = true)
    public Account getMyAccount(UUID accountId, UUID userId) {

        return accountRepository
            .findByIdAndUserId(accountId, userId)
            .orElseThrow(AccountNotFoundException::new);
}
}

