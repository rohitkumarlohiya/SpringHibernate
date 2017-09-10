package com.mcloud.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mcloud.dao.DataDao;
import com.mcloud.entities.AbstractEntity;
import com.mcloud.entities.CustomUser;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	
	 /* @Autowired private AutoUserRepository repo;
	  
	  @Override public UserDetails loadUserByUsername(String username) throws
	  UsernameNotFoundException { return repo.findByUsername(username); }*/
	 

	@Autowired
	DataDao<AbstractEntity> dataDao;

	static final Logger logger = Logger.getLogger(CustomUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		CustomUser accounts = null;
		try {
			accounts = dataDao.findByUserName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (accounts == null) {
			throw new UsernameNotFoundException("Username not found");
		}

		return accounts;
	}
}
