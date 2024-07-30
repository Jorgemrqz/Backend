package ec.edu.ups.pw.ProyectoFinalBackend.service;

import ec.edu.ups.pw.ProyectoFinalBackend.bussines.UserManagement;
import ec.edu.ups.pw.ProyectoFinalBackend.jwt.AuthUtils;
import ec.edu.ups.pw.ProyectoFinalBackend.jwt.Token;
import ec.edu.ups.pw.ProyectoFinalBackend.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class LoginService {
	
	@Inject
	UserManagement um;
	
	@POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User credentials) {
        try {
            // Validar las credenciales del usuario
            User user = um.getUser(credentials.getEmail());
            
            if (user != null) {
                // Generar el token
                String host = "biblioteca"; // Reemplaza con tu dominio
                Token token = AuthUtils.createToken(host, user);
                
                // Devolver el token en la respuesta
                return Response.ok(token).build();
            } else {
                // Si las credenciales no son válidas, devolver un error
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciales inválidas")
                    .build();
            }
        } catch (Exception e) {
            // Manejar cualquier excepción y devolver un error interno del servidor
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Credenciales invalidas")
                .build();
        }
    }

}
