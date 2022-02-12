package com.seg.client.web.blueprint;

import java.io.IOException;
import java.net.ConnectException;
import java.util.function.Function;

import com.seg.precaution.exception.ApplicationException;
import com.seg.precaution.exception.ConnectionException;

import org.springframework.core.codec.DecodingException;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public interface ExceptionWebClientHandle {
    
    String DECODE_RESPONSE = "Ocurrio un error al leer la respuesta del servidor";
    String CLOSED_CONNECTION = "El servidor cerro la conexión prematuramente";
	String NO_RESPONSE_DELAY = "No se obtuvo respuesta del servidor en el tiempo estipulado";
	String NO_RESPONSE = "No se obtuvo respuesta del servidor";
	String CONNECTION_ERROR = "Ocurrio un error al intentar conectarse con el servidor";

    default Function<? super Throwable, ? extends Throwable> handleExceptions(){
		return (error) -> {
			if (!(error instanceof ConnectionException)){
				if (error instanceof WebClientRequestException || error instanceof WebClientResponseException){
					error.printStackTrace();
					return returnApplicationException(CONNECTION_ERROR);
				}
				if (error instanceof ConnectException){
					error.printStackTrace();
					return returnApplicationException(NO_RESPONSE_DELAY);
				}
				else if (error instanceof DecodingException || error instanceof UnsupportedMediaTypeException){		
					error.printStackTrace();
					return returnApplicationException(DECODE_RESPONSE);
				}
				else if (error instanceof IOException){
					error.printStackTrace();
					return returnApplicationException(CLOSED_CONNECTION);
				}
				else if (error instanceof IllegalStateException){
					error.printStackTrace();
					return returnApplicationException(NO_RESPONSE);
				}				
				System.out.println(error.getClass().toString());
				error.printStackTrace();
				return returnApplicationException(CONNECTION_ERROR);
			}

			return error;
		};
	}

    default ApplicationException returnApplicationException(final String mensaje){
		return new ApplicationException(mensaje);
	}
}
