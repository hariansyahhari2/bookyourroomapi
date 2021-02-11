package com.hariansyah.bookyourrooms.api.configs;

import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.models.entitymodels.CustomerIdentityModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(CustomerIdentityModel.class, CustomerIdentity.class).addMappings(mapper ->
                mapper.skip(CustomerIdentity::setId));
        return modelMapper;
    }
}
