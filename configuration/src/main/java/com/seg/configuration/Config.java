package com.seg.configuration;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:client-application.properties")
@PropertySource("classpath:preloader-application.properties")
public class Config implements ApplicationContextAware{

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
    
    @Bean    
    // @Scope("prototype")
    public ModelMapper modelMapper(){
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }   

    // @Bean(name = "modelMapperUsuario")
    public ModelMapper modelMapperUsuario(final ModelMapper modelMapper){
        // modelMapper.typeMap(UsuarioPropiedades.class, Usuario.class).addMappings(mapper -> {            
        //     mapper.skip(Usuario::setComision);
        //     mapper.skip(Usuario::setComisionada);
        // });        
        return modelMapper;
    }

    // @Bean(name = "modelMapperArchivo")
    public ModelMapper modelMapperArchivo(final ModelMapper modelMapper){
        // modelMapper.typeMap(UsuarioPropiedades.class, Usuario.class).addMappings(mapper -> {            
        //     mapper.skip(Usuario::setComision);
        //     mapper.skip(Usuario::setComisionada);
        // });        
        return modelMapper;
    }
}
