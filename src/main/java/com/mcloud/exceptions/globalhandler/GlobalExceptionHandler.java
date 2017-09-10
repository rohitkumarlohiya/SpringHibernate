package com.mcloud.exceptions.globalhandler;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mcloud.exceptions.custom.BadRequestException;
import com.mcloud.exceptions.custom.EntityNotFoundException;
import com.mcloud.exceptions.custom.ListNotFoundException;
import com.mcloud.exceptions.custom.MandatoryFieldException;
import com.mcloud.exceptions.custom.NotAuthorisedException;
import com.mcloud.exceptions.custom.SomethingWentWrongException;
import com.mcloud.exceptions.custom.TokenRemovedException;
import com.mcloud.models.commons.Status;

@ControllerAdvice
@Component
@PropertySource("classpath:message.properties")
public class GlobalExceptionHandler {

	static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

	@Autowired
	Environment env;

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ListNotFoundException.class)
	@ResponseBody
	public Status listNotFound() {
		return new Status(HttpStatus.NOT_FOUND.toString(), env.getProperty("exception.listnotfound"));
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@ExceptionHandler(TokenRemovedException.class)
	@ResponseBody
	public Status tokenRemoved() {
		return new Status(HttpStatus.ACCEPTED.toString(), env.getProperty("exception.tokenremoved"));
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	public Status entityNotFound() {
		return new Status(HttpStatus.NOT_FOUND.toString(), env.getProperty("exception.entitynotfound"));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(SomethingWentWrongException.class)
	@ResponseBody
	public Status somethingWentWrong() {
		return new Status(HttpStatus.INTERNAL_SERVER_ERROR.toString(), env.getProperty("exception.internalserver"));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MandatoryFieldException.class)
	@ResponseBody
	public Status mandatory(MandatoryFieldException mfe) {
		/*
		 * for(Errors error : mfe.getErrors()){ logger.info(error.getField()
		 * +"-"+ error.getErrorMessage()); }
		 */
		return new Status(HttpStatus.BAD_REQUEST.toString(), env.getProperty("exception.mandatory.fields"),
				mfe.getErrors());
	}

	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public Status badRequest(BadRequestException e) {
		return new Status(HttpStatus.BAD_REQUEST.toString(), e.toString());
	}

	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(HibernateException.class)
	@ResponseBody
	public Status hibernateException(HibernateException e) {
		e.printStackTrace();
		return new Status(HttpStatus.EXPECTATION_FAILED.toString(), e.toString());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Status exception(Exception e) {
		e.printStackTrace();

		if (e.getClass().getName().equals("java.lang.IllegalArgumentException")
				&& e.getMessage().equals("attempt to create delete event with null entity")) {
			return new Status(HttpStatus.BAD_REQUEST.toString(), "Requested resource not exist in the system");
		} else {
			return new Status(HttpStatus.SEE_OTHER.toString(), e.toString());
		}
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(NotAuthorisedException.class)
	@ResponseBody
	public Status exception(NotAuthorisedException e) {
		e.printStackTrace();
		return new Status(HttpStatus.UNAUTHORIZED.toString(), e.toString());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InternalAuthenticationServiceException.class)
	@ResponseBody
	public Status exception(InternalAuthenticationServiceException e) {
		e.printStackTrace();
		return new Status(HttpStatus.UNAUTHORIZED.toString(), env.getProperty("username.incorrect"));
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(com.mcloud.exceptions.custom.ConstraintViolationExceptionCustom.class)
	@ResponseBody
	public Status exception(com.mcloud.exceptions.custom.ConstraintViolationExceptionCustom e) {
		e.printStackTrace();
		return new Status(HttpStatus.CONFLICT.toString(), env.getProperty("constraint.duplicate.violation"));
	}

	/*
	 * @ResponseStatus(HttpStatus.UNAUTHORIZED)
	 * 
	 * @ExceptionHandler(ConstraintViolationException.class)
	 * 
	 * @ResponseBody public Status exception(ConstraintViolationException e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * if (e.getErrorCode() == 1062) return new
	 * Status(HttpStatus.UNAUTHORIZED.toString(),
	 * env.getProperty("constraint.duplicate.violation")); return new
	 * Status(HttpStatus.UNAUTHORIZED.toString(),
	 * env.getProperty("constraint.violation")); }
	 */

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public Status exception(ConstraintViolationException e) {

		e.printStackTrace();
		logger.info(e.getMessage());
		return new Status(HttpStatus.CONFLICT.toString(), env.getProperty("constraint.violation"));
	}

	// stripe exception

	/*
	 * @ResponseStatus(HttpStatus.UNAUTHORIZED)
	 * 
	 * @ExceptionHandler(AuthenticationException.class)
	 * 
	 * @ResponseBody public Status
	 * authenticationException(AuthenticationException e) { e.printStackTrace();
	 * return new Status(HttpStatus.NOT_FOUND.toString(),
	 * env.getProperty("exception.stripe.authentication")); }
	 * 
	 * @ResponseStatus(HttpStatus.BAD_REQUEST)
	 * 
	 * @ExceptionHandler(InvalidRequestException.class)
	 * 
	 * @ResponseBody public Status
	 * invalidRequestException(InvalidRequestException e) { e.printStackTrace();
	 * return new Status(HttpStatus.NOT_FOUND.toString(),
	 * env.getProperty("exception.stripe.invalidrequest")); }
	 * 
	 * @ResponseStatus(HttpStatus.BAD_GATEWAY)
	 * 
	 * @ExceptionHandler(APIConnectionException.class)
	 * 
	 * @ResponseBody public Status aPIConnectionException(APIConnectionException
	 * e) { e.printStackTrace(); return new
	 * Status(HttpStatus.NOT_FOUND.toString(),
	 * env.getProperty("exception.stripe.apiconnectionexception")); }
	 * 
	 * @ResponseStatus(HttpStatus.CHECKPOINT)
	 * 
	 * @ExceptionHandler(CardException.class)
	 * 
	 * @ResponseBody public Status cardException(CardException e) {
	 * e.printStackTrace(); return new Status(HttpStatus.NOT_FOUND.toString(),
	 * env.getProperty("exception.stripe.cardexception")); }
	 * 
	 * @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	 * 
	 * @ExceptionHandler(APIException.class)
	 * 
	 * @ResponseBody public Status aPIException(APIException e) {
	 * e.printStackTrace(); return new Status(HttpStatus.NOT_FOUND.toString(),
	 * env.getProperty("exception.stripe.apiexception")); }
	 */

}
