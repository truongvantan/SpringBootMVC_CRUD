package com.tvt.dto;

import com.tvt.constant.Constant;
import com.tvt.entity.Account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountDTO {

	@NotBlank(message = "Vui lòng nhập tên đăng nhập")
	@NotNull(message = "Vui lòng nhập tên đăng nhập")
	private String username;

	@NotBlank(message = "Vui lòng nhập mật khẩu")
	@Pattern(regexp = Constant.PASSWORD_REGEX, message = "Mật khẩu phải có ít nhất 6 kí tự bao gồm số, chữ cái và kí tự đặc biệt")
	private String password;

	public AccountDTO() {
		super();
	}

	public AccountDTO(@NotNull(message = "Vui lòng nhập username") String username,
			@NotNull(message = "Vui lòng nhập password") String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public AccountDTO(Account a) {
		this(a.getUsername(), a.getPassword());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
