package com.Jobsity.utils;

import java.util.Base64;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordProtector {

	public static void encryptPassword(String password) {
		String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
		System.out.println(encodedPassword);

	}

	public static String decryptPassword(String encodedPassword) {
		String decodedPassword = new String(Base64.getDecoder().decode(encodedPassword.getBytes()));
		return decodedPassword;

	}

}
