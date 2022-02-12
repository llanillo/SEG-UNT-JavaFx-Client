package com.seg.client.web.blueprint;

import com.seg.domain.jwt.TokenResponse;

import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseWebClient implements ExceptionWebClientHandle{

	private static TokenResponse token;
	
	protected final WebClient webClient;

	public static TokenResponse getToken() {
		if (token != null)
			return token;
		else return new TokenResponse("", "", "");
	}

	public static void setToken(final TokenResponse tokenResponse) {
		if (tokenResponse != null)
			BaseWebClient.token = tokenResponse;		
	}	
}
