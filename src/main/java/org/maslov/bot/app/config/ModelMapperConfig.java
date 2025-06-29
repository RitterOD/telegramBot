package org.maslov.bot.app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ModelMapperConfig {

    @Primary
    @Bean
    public ModelMapper modelMapper() {
        final var modelMapper = new ModelMapper();
        return modelMapper;
    }
}
