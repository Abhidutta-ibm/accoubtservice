package com.ibm.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.dto.AccountDto;
import com.ibm.dto.UserLogin;
import com.ibm.model.Account;
import com.ibm.repo.AccountRepo;
import com.ibm.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountRepo accountRepo;

	@Override
	public String registerUser(AccountDto accountDto) {
		Account acc = new Account();
		BeanUtils.copyProperties(accountDto, acc);
		Account save = accountRepo.save(acc);
		return save.getAccountId() + "Account succesfully created.";
	}

	@Override
	public String logIn(UserLogin uli) {
		Account user = accountRepo.findByName(uli.getUserName());
		if (user == null) {
			throw new RuntimeException("User does not exist.");
		}
		if (!user.getPassword().equals(uli.getPassword())) {
			throw new RuntimeException("Password mismatch.");
		}

		return "Login success";
	}

	@Override
	public String logOut(String userName) {
		Account findByName = accountRepo.findByName(userName);
		findByName.setExpiredTime(LocalDateTime.now());
		return "1";
	}

	@Override
	public String sessionCheck(String userName) {
		Account findByName = accountRepo.findByName(userName);
		LocalDateTime expiredTime = findByName.getExpiredTime();
		return expiredTime.toString();
	}

}
