package ec.edu.ups.pw.ProyectoFinalBackend.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
	
	private String token;
	
	public Token(@JsonProperty("token") String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}