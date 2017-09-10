package com.mcloud.security.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.InMemoryTokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutImplementation implements LogoutSuccessHandler {

	static final Logger logger = Logger.getLogger(LogoutImplementation.class);

	@Autowired
	InMemoryTokenStore tokenstore;

	@Override
	public void onLogoutSuccess(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Authentication paramAuthentication)
			throws IOException, ServletException {

		String nextView = "logout/success";

		try {
			removeaccess(paramHttpServletRequest);
		} catch (Exception e) {
			nextView = "logout/failure";
		}
		/*
		 * RequestDispatcher dispatcher = paramHttpServletRequest
		 * .getServletContext().getRequestDispatcher(nextView);
		 * dispatcher.forward(paramHttpServletRequest,
		 * paramHttpServletResponse);
		 */
		paramHttpServletResponse.sendRedirect(nextView);
		// throw new TokenRemovedException();

		/*
		 * paramHttpServletResponse .getOutputStream() .write(
		 * "{\"code\":\"200\",\"message\": \"Logout Successful\",\"validationErrors\": null}"
		 * .getBytes());
		 */

	}

	public void removeaccess(HttpServletRequest req) {

		String tokens = req.getParameter("access_token").trim();
		logger.info("token: " + tokens);

		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(tokens);
		logger.info("token: " + token);
		tokenstore.removeAccessToken(tokens);
		logger.info("\n\tAccess Token Removed Successfully!!!!!!!!");

	}

}
