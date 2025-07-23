package org.maslov.bot.app.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Date;
import java.util.UUID;

@Configuration
public class ModelMapperConfig {

    @Primary
    @Bean
    public ModelMapper modelMapper() {
        final var modelMapper = new ModelMapper();

        modelMapper.addConverter(new AbstractConverter<String, UUID>() {
            @Override
            protected UUID convert(String source) {
                return (source == null) ? null : UUID.fromString(source);
            }
        });


        modelMapper.addConverter(new AbstractConverter<UUID, String>() {
            @Override
            protected String convert(UUID source) {
                return (source == null) ? null : source.toString();
            }
        });

        return modelMapper;
    }
}
