package com.tvt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tvt.dto.AccountDTO;
import com.tvt.entity.Account;
import com.tvt.service.AccountService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private AccountService accountService;

	@GetMapping({ "/", "/login" })
	public String showLogin(Model model, @RequestParam(value = "error", required = false) String error) {
		AccountDTO a = new AccountDTO();
		model.addAttribute("account", a);
		
		if ("1".equals(error)) {
			model.addAttribute("errorMessage", "Vui lòng đăng nhập để sử dụng hệ thống");
		}
		
		return "views/login";
	}

	@PostMapping("/checkLogin")
	public String checkLogin(Model model, @Valid @ModelAttribute("account") AccountDTO a, BindingResult bindingResult,
			HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "views/login";
		} else {
			Account account = accountService.findByUsernameAndPassword(a.getUsername(), a.getPassword());
			if (account == null) {
				model.addAttribute("errorMessage", "Thông tin đăng nhập không chính xác");
				return "views/login";
			} else if ("admin".equals(account.getUsername())) {
				session.setAttribute("sessionAccount", account.getUsername() + "(admin)");
				return "views/welcomeAdmin";
			} else {
				session.setAttribute("sessionAccount", account.getUsername() + "(user)");
				return "views/welcomeUser";
			}
		}
	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		return "redirect:/login?error=1";
	}
}
