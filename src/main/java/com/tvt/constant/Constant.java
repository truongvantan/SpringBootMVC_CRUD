package com.tvt.constant;

public class Constant {
	/**
	 * Password phải có ít nhất 1 kí tự số [0-9].
	 * Password phải có ít nhất 1 kí tự chữ cái in thường [a-z].
	 * Password phải có ít nhất 1 kí tự chữ cái in hoa [A-Z].
	 * Password phái có ít nhất 1 kí tự đặc biệt ! @ # & ( ).
	 * Password có độ dài ít nhất là 6 kí tự và tối đa 20 kí tự.
	 */
	public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$";
	public static final int NUM_OF_PRODUCTS_PER_PAGE = 10;
}
