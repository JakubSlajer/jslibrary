package com.etnetera.jslibrary.config.dozermapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerMapperConfiguration {

    @Bean
    public Mapper mapper(){
        return DozerBeanMapperBuilder.buildDefault();
    }

}
