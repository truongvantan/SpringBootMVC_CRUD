package com.tvt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvt.entity.Account;

@Repository("accountRepository")
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Optional<Account> findByUsernameAndPassword(String username, String password);

}
