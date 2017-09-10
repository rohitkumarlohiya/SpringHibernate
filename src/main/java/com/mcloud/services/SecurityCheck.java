package com.mcloud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mcloud.entities.AbstractEntity;

public class SecurityCheck {

	@Autowired
	static DataServices<AbstractEntity> dataServices;

	public static boolean hasRole(String role) {
		try {
			SecurityContext context = SecurityContextHolder.getContext();
			if (context == null)
				return false;

			Authentication authentication = context.getAuthentication();
			if (authentication == null)
				return false;

			for (GrantedAuthority auth : authentication.getAuthorities()) {
				if (role.equals(auth.getAuthority()))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
