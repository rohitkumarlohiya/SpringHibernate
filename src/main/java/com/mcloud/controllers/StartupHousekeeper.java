package com.mcloud.controllers;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupHousekeeper implements ApplicationListener<ContextRefreshedEvent> {

	/*
	 * @Autowired CustomServices customServices;
	 */

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		// customServices.getLocalizationKeyValueJsonByCountryCodeAndLanguageCode("US",
		// "en", "USER", "en-US");
	}
}