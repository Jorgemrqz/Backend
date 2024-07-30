package ec.edu.ups.pw.ProyectoFinalBackend.jwt;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import org.joda.time.DateTime;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import ec.edu.ups.pw.ProyectoFinalBackend.bussines.UserManagement;
import ec.edu.ups.pw.ProyectoFinalBackend.enums.Rol;
import ec.edu.ups.pw.ProyectoFinalBackend.model.User;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter, ContainerResponseFilter {
	
	@Inject
	private UserManagement userM;

	// Para resolver los problemas de CORS
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		// TODO Auto-generated method stub
		responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "content-type, Authorization");
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub)
		SecurityContext originalContext = requestContext.getSecurityContext();
		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || authHeader.isEmpty() || authHeader.split(" ").length != 2) {
			Authorizer authorizer = new Authorizer("", originalContext.isSecure());
			requestContext.setSecurityContext(authorizer);
		} else {
			JWTClaimsSet claimSet;
			try {
				claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
			} catch (ParseException pe) {
				// TODO: handle exception
				throw new IOException("Error decoding the jwt");
			} catch (JOSEException je) {
				// TODO: handle exception
				throw new IOException("Invalid token");
			}
			if(new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {
				throw new IOException("Expired token");
			} else {
				ec.edu.ups.pw.ProyectoFinalBackend.model.User user = this.userM.getUser(claimSet.getSubject());
				Authorizer authorizer = new Authorizer(user.getEmail(), user.getRol(), originalContext.isSecure());
				requestContext.setSecurityContext(authorizer);
			}
		}
	}
	
	public static class Authorizer implements SecurityContext {

		String userName;
		Rol role;
		
		// Nos permitira validad que la conexion sea segura
		boolean isSecure;
		
		public Authorizer(String userName, boolean isSecure) {
			this.userName = userName;
			this.isSecure = isSecure;
		}

		public Authorizer(String userName, Rol role, boolean isSecure) {
			super();
			this.userName = userName;
			this.role = role;
			this.isSecure = isSecure;
		}

		@Override
		public Principal getUserPrincipal() {
			// TODO Auto-generated method stub
			return new User(this.userName);
		}

		@Override
		public boolean isUserInRole(String role) {
			// TODO Auto-generated method stub
			if(Rol.ADMIN.toString().equalsIgnoreCase(role) || Rol.CLIENT.toString().equalsIgnoreCase(role)) {
				return true;
			}
			return false;
		}

		@Override
		public boolean isSecure() {
			// TODO Auto-generated method stub
			return this.isSecure;
		}

		@Override
		public String getAuthenticationScheme() {
			// TODO Auto-generated method stub
			return "JWT";
		}
		
	}
	
	public static class User implements Principal {
		
		String name;
		
		public User(String name) {
			super();
			this.name = name;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return this.name;
		}
		
	}

}
