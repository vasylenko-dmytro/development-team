package com.vasylenko.application.config;

import com.vasylenko.application.util.CustomLogger;
import com.vasylenko.application.util.PhoneNumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Locale;

/**
 * Web MVC configuration.
 * <p> Configures the application with the following beans:
 * <ul> <li> A locale resolver with a default locale. </li>
 * <li> A locale change interceptor to allow changing the current locale. </li>
 * <li> A URL builder scoped to the current request. </li></ul>
 * </p>
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final CustomLogger customLogger;

    @Autowired
    public WebMvcConfiguration(CustomLogger customLogger) {
        this.customLogger = customLogger;
    }

    /**
     * Configures the locale resolver with a default locale.
     *
     * @return the configured locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        customLogger.info(String.format("Configuring locale resolver with default locale: %s", Locale.US));

        SessionLocaleResolver sessionLocaleResolver=new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    /**
     * Configures the locale change interceptor to allow changing the current locale.
     *
     * @return the configured locale change interceptor
     */
    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        customLogger.info("Configuring locale change interceptor with parameter name 'lang'");

        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    /**
     * Provides a URL builder scoped to the current request.
     *
     * @return the URL builder
     */
    @Bean
    @RequestScope
    public ServletUriComponentsBuilder urlBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequest();
    }

    /**
     * Adds the locale change interceptor to the registry.
     *
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        customLogger.info("Adding locale change interceptor to the registry");

        registry.addInterceptor(localeInterceptor());
    }

    /**
     * Adds custom formatters to the registry.
     *
     * @param registry the formatter registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        customLogger.info("Adding custom formatters to the registry");

        registry.addFormatter(new PhoneNumberFormatter());
    }
}
