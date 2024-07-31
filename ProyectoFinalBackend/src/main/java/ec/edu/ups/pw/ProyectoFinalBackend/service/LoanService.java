package ec.edu.ups.pw.ProyectoFinalBackend.service;

import java.util.List;
import ec.edu.ups.pw.ProyectoFinalBackend.bussines.LoanManagement;
import ec.edu.ups.pw.ProyectoFinalBackend.model.Loan;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/loan")
public class LoanService {

    @Inject
    LoanManagement lm;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLoans() {
        List<Loan> loans = this.lm.getLoans();
        return Response.ok(loans).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getLoan(@PathParam("id") int id) {
        try {
            Loan loan = this.lm.getLoan(id);
            return Response.ok(loan).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error al obtener el préstamo").build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createLoan(Loan loan) {
        try {
            this.lm.insertLoan(loan);
            return Response.ok(loan).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al crear el préstamo: " + e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/return/{id}")
    public Response returnLoan(@PathParam("id") int id) {
        try {
            this.lm.returnLoan(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al devolver el préstamo").build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{email}")
    public Response getLoansByUserEmail(@PathParam("email") String email) {
        try {
            List<Loan> loans = this.lm.getLoansByUserEmail(email);
            return Response.ok(loans).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al obtener los préstamos del usuario: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/report/top-user")
    public Response getTopUser() {
        try {
            Object[] topUser = this.lm.getTopUser();
            return Response.ok(topUser).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al obtener el reporte de usuario: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/report/top-book")
    public Response getTopBook() {
        try {
            Object[] topBook = this.lm.getTopBook();
            return Response.ok(topBook).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al obtener el reporte de libro: " + e.getMessage()).build();
        }
    }
}