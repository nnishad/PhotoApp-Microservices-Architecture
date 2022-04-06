package com.nikhilnishad.photoApp.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthHeaderFilter extends AbstractGatewayFilterFactory<AuthHeaderFilter>{
	@Autowired
	private Environment env;
	
	
//	public AuthHeaderFilter() {
//		super(Config.class);
//	}
	
	public static class Config{
		
	}

	@Override
	public GatewayFilter apply(AuthHeaderFilter config) {
		return (exchange,chain)->{
			
			ServerHttpRequest request= exchange.getRequest();
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange,"No authorization heade",HttpStatus.UNAUTHORIZED);
			}
			String authHeader=request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt=authHeader.replace("Bearer","");
			
			if(!isJWTValid(jwt)) {
				return onError(exchange,"JWT Token is not valid",HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange);
		};
	}
	
	private Mono<Void> onError(ServerWebExchange exchange,String err,HttpStatus httpStatus){
		ServerHttpResponse response =exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private boolean isJWTValid(String token) {
		boolean returnValue=true;
		
		String subject=Jwts.parser()
				.setSigningKey(env.getProperty("token.secret"))
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
		if(subject==null|| subject.isEmpty()) {
			returnValue=false;
		}
		return returnValue;
	}
}
