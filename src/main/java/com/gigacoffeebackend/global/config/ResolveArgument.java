package com.gigacoffeebackend.global.config;

import com.gigacoffeebackend.global.aop.annotation.HandleCurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ResolveArgument implements WebMvcConfigurer {

    private final HandleCurrentUser handleCurrentUser;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(handleCurrentUser);
    }
}