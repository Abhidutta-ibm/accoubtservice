package com.ibm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.model.Account;

public interface AccountRepo extends JpaRepository<Account, Integer> {
	Account findByName(String userName);
}
