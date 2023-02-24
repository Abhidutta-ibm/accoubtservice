package com.ibm.service;

import com.ibm.dto.AccountDto;
import com.ibm.dto.UserLogin;

public interface AccountService {
	public String registerUser(AccountDto accountDto);

	public String logIn(UserLogin uli);

	public String logOut(String userName);

	public String sessionCheck(String userName);
}
