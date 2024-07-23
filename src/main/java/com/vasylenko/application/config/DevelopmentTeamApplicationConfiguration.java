package com.vasylenko.application.config;

import com.vasylenko.application.util.InMemoryUniqueIdGenerator;
import com.vasylenko.application.util.UniqueIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.UUID;

@Configuration
public class DevelopmentTeamApplicationConfiguration {

	@Bean
	public ITemplateResolver svgTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix("classpath:/templates/svg/");
		resolver.setSuffix(".svg");
		resolver.setTemplateMode("XML");

		return resolver;
	}

	@Bean
	public UniqueIdGenerator<UUID> uniqueIdGenerator() {
		return new InMemoryUniqueIdGenerator();
	}
}
