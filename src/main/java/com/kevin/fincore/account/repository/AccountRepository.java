package com.kevin.fincore.account.repository;

import com.kevin.fincore.account.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findAllByUserId(UUID userId);
}

