package com.tvt.service;

import com.tvt.entity.Account;

public interface AccountService {

	Account findByUsernameAndPassword(String username, String password);

}
