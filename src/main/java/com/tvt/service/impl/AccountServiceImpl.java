package com.tvt.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tvt.entity.Account;
import com.tvt.repository.AccountRepository;
import com.tvt.service.AccountService;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account findByUsernameAndPassword(String username, String password) {
		Optional<Account> a = accountRepository.findByUsernameAndPassword(username, password);
		if (!a.isPresent()) {
			return null;
		} else  {
			return a.get();
		}
	}
	
}
