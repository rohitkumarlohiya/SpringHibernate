package com.mcloud.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.model.Token;

@Component
public class StripeUtils {

	private final String API_KEY = "sk_test_rRV2fzxskgxlPM6dswuiVS1D";

	public Token retrieveToken(String cardToken) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		Token token = null;
		Stripe.apiKey = API_KEY;
		token = Token.retrieve(cardToken);
		return token;

	}

	public Charge createCharge(Object ammount, Object currency, Object cardToken, Object chargeDescription)
			throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException,
			APIException {
		Charge charge = null;
		Stripe.apiKey = API_KEY;

		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("amount", ((Double) ammount).intValue());
		chargeParams.put("currency", currency);
		chargeParams.put("source", cardToken);
		chargeParams.put("description", chargeDescription);

		charge = Charge.create(chargeParams);

		return charge;
	}

	public Charge retriveCharge(String chargeToken) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {

		Charge charge = null;
		Stripe.apiKey = API_KEY;

		charge = Charge.retrieve(chargeToken);

		return charge;
	}

	public Refund refund(String chargeToken) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {

		Stripe.apiKey = API_KEY;

		Map<String, Object> refundParams = new HashMap<String, Object>();
		refundParams.put("charge", chargeToken);

		Refund refund = Refund.create(refundParams);
		return refund;
	}
}
