package com.vasylenko.application.config;

import com.vasylenko.application.service.StubUserDetailsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@TestConfiguration
@Import(WebSecurityConfiguration.class)
public class SpringSecurityTestConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        public ITemplateResolver svgTemplateResolver() {
            SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
            resolver.setPrefix("classpath:/templates/svg/");
            resolver.setSuffix(".svg");
            resolver.setTemplateMode("XML");

            return resolver;
        }

        @Bean
        public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
            return new StubUserDetailsService(passwordEncoder);
        }
}
