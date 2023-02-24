package com.ibm.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.zip.Deflater;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.dto.AccountDto;
import com.ibm.dto.UserLogin;
import com.ibm.service.AccountService;

@RestController
public class AccountController {
	@Autowired
	AccountService accountService;

	@PostMapping("/account")
	ResponseEntity<String> registerUser(@RequestBody AccountDto accountDto) {
		byte[] accountImage = accountDto.getAccountImage();
		if(accountImage!=null) {
			byte[] compressBytes = compressBytes(accountImage);
			accountDto.setAccountImage(compressBytes);
		}
		LocalDateTime now = LocalDateTime.now();
		accountDto.setCreatedDate(now);
		accountDto.setLogInTime(now);
		accountDto.setExpiredTime(now.plusMinutes(10));
		
		String registerUser = accountService.registerUser(accountDto);

		return new ResponseEntity<>(registerUser, HttpStatus.OK);
	}

	@PostMapping("/logIn")
	ResponseEntity<String> logIn(@RequestBody UserLogin uli) {
		String logIn = accountService.logIn(uli);

		return new ResponseEntity<>(logIn, HttpStatus.OK);
	}

	@PostMapping("/logOut/{userName}")
	ResponseEntity<String> logOut(@PathVariable String userName) {
		String logOut = accountService.logOut(userName);

		return new ResponseEntity<>(logOut, HttpStatus.OK);
	}

	@PostMapping("/sessionCheck/{userName}")
	ResponseEntity<String> sessionCheck(@PathVariable String userName) {
		String sessionCheck = accountService.sessionCheck(userName);
		return new ResponseEntity<>(sessionCheck, HttpStatus.OK);
	}
	
	
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

}
