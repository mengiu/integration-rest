package com.bank.diwa0.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class TestSecurityServiceImpl extends BaseServiceImpl implements TestSecurityService {

	@Override
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public String createResource() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "Hello " + authentication;
	}

}
