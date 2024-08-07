package com.vasylenko.application.config;

import com.vasylenko.application.util.CustomLogger;
import com.vasylenko.application.util.InMemoryUniqueIdGenerator;
import com.vasylenko.application.util.UniqueIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.UUID;

/**
 * Application configuration.
 * <p> Configures the application with the following beans:
 * <ul> <li> A template resolver for SVG files. </li>
 * <li> A unique ID generator. </li>
 * <li> A local validator factory bean with a custom message source. </li>
 * <li> A password encoder. </li> </ul>
 * </p>
 */
@Configuration
public class ApplicationConfiguration {

	private final CustomLogger customLogger;

	@Autowired
	public ApplicationConfiguration(CustomLogger customLogger) {
		this.customLogger = customLogger;
	}

	/**
	 * Configures a template resolver for SVG files.
	 *
	 * @return the configured template resolver
	 */
	@Bean
	public ITemplateResolver svgTemplateResolver() {
		customLogger.info("Configuring SVG template resolver");

		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix("classpath:/templates/svg/");
		resolver.setSuffix(".svg");
		resolver.setTemplateMode("XML");
		resolver.setCacheable(false);
		return resolver;
	}

	/**
	 * Provides a unique ID generator.
	 *
	 * @return the unique ID generator
	 */
	@Bean
	public UniqueIdGenerator<UUID> uniqueIdGenerator() {
		customLogger.info("Providing unique ID generator");

		return new InMemoryUniqueIdGenerator();
	}

	/**
	 * Configures the local validator factory bean with a custom message source.
	 *
	 * @param messageSource the message source for validation messages
	 * @return the configured local validator factory bean
	 */
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
		customLogger.info("Configuring local validator factory bean with custom message source");

		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

	/**
	 * Provides a password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		customLogger.info("Providing password encoder");

		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
