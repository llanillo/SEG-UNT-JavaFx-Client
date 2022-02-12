package com.seg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.seg.configuration.Config;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

@SpringBootApplication
public class Main {
    
    private static final String PROPERTIES_PATH = "/preloader-application.properties";
    private static final String ERROR_JAVAFX = "Could not load javafx properties";

    public static void main(final String[] args) {           
        try {
            final Properties properties = new Properties(System.getProperties());
            final InputStream inputStream = Config.class.getResourceAsStream(PROPERTIES_PATH);
            properties.load(inputStream);                    
            System.setProperties(properties);            
        }
        catch (final IOException e){
            System.out.println(ERROR_JAVAFX);
            e.printStackTrace();
        }
        
        Application.launch(ApplicationFx.class, args);        
    }

    // @Bean
    // CommandLineRunner extenderToken(final ClienteWebToken clienteWebToken){
    //     return args -> {
    //         final TokenExtender tokenExtender = new TokenExtender("refreshToken");
    //         final ServicioPrueba servicioPrueba = new ServicioPrueba(clienteWebToken, tokenExtender);
    //         servicioPrueba.start();
    //     };
    // }

    // @Bean
    // CommandLineRunner run(final ClienteWebSesion clienteWebSesion){
    //     return (args) -> {
    //         // final String valor = clienteWebSesion.prueba();
    //         // System.out.println("Respuesta " + valor);
    //         final JwtRespuesta respuesta = clienteWebSesion.iniciarSesion("13", "13");
    //         System.out.println(respuesta.getId());
    //         System.out.println(respuesta.getEmail());
    //         System.out.println(respuesta.getUsuario());
    //         System.out.println(respuesta.getAccessToken());
    //         System.out.println(respuesta.getRefreshToken());
    //         System.out.println(respuesta.getTipo());
    //         respuesta.getRoles().stream().forEach(System.out::println);
    //     };
    // }

    // @Bean
    // CommandLineRunner buscarUsuario(final ClienteWebSesion clienteWebSesion){
    //     return args -> {
    //         final UsuarioFX usuarioRespuesta = clienteWebSesion.buscarPorId(30l);
    //         System.out.println(usuarioRespuesta.getApellido());
    //         System.out.println(usuarioRespuesta.getNombre());
    //         System.out.println(usuarioRespuesta.getEmail());
    //         System.out.println(usuarioRespuesta.getDni());
    //         System.out.println(usuarioRespuesta.getCuil());
    //         usuarioRespuesta.getCarrera().stream().forEach((e) -> System.out.println(e.toString()));
    //     };
    // }
}
