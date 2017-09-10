package com.mcloud.controllers;

import org.hibernate.HibernateException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcloud.models.commons.Status;

@Controller
@RequestMapping("/logout/")
@PropertySource("classpath:message.properties")
public class LogoutController {
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	@ResponseBody
	public Status success() throws HibernateException, Exception {
		return new Status("200", "Logout Successful");

	}

	@RequestMapping(value = "/failure", method = RequestMethod.GET)
	@ResponseBody
	public Status failure() throws HibernateException, Exception {
		return new Status("500", "Logout Failure");

	}
}
