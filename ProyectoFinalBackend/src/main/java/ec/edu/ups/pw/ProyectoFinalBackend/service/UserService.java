package ec.edu.ups.pw.ProyectoFinalBackend.service;

import java.util.List;

import ec.edu.ups.pw.ProyectoFinalBackend.bussines.UserManagement;
import ec.edu.ups.pw.ProyectoFinalBackend.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserService {
	
	@Inject
	UserManagement um;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		List<User> users = this.um.getUsers();
		return Response.ok(users).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{email}/{password}")
	public Response getUser(@PathParam("email") String email, @PathParam("password") String password) {
		User user = this.um.getUser(email);
		if(user.getPassword().equals(password)) {
			return Response.ok(user).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales Invalidas").build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	public Response createUser(User user) {
		try {
			this.um.insertUSer(user);
			return Response.ok(user).build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("El correo electronico ya se encuentra asociado a una cuenta").build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{email}")
	public Response deleteUser(@PathParam("email") String email) {
		try {
			this.um.deleteUser(email);
			return Response.ok().build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("No se ha podido eliminar el usauario solicitado").build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update")
	public Response updateUser(User user) {
		try {
			this.um.updateUser(user);
			return Response.ok(user).build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("Error al intentar actualizar las credenciales").build();
		}
	}
}
